package br.com.zupacademy.metricas.biometria;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.zupacademy.metricas.cartao.Cartao;
import br.com.zupacademy.metricas.cartao.CartaoRepository;

@RestController
public class BiometriaController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@PostMapping("cartao/{codigoCartao}/biometria")
	public ResponseEntity<?> novaBiometria(@PathVariable String codigoCartao,
			@Valid @RequestBody BiometriaForm form) {
		Optional<Cartao> cartaoOpt = cartaoRepository.findByCodigoCartao(codigoCartao);
		
		if(cartaoOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cartao cartao = cartaoOpt.get();
		
		Biometria biomatria = form.map(cartao);
		cartao.novaBiometrias(biomatria);
		cartao = cartaoRepository.save(cartao);
		biomatria = cartao.ultimaBiometria();
		
		Assert.state(biomatria.getId() != null,"Biometria deveria ter um id neste ponto");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{idBiometria}").buildAndExpand(biomatria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("cartao/{codigoCartao}/biometria")
	public ResponseEntity<?> lista(@PathVariable String codigoCartao) {
		Optional<Cartao> cartaoOpt = cartaoRepository.findByCodigoCartao(codigoCartao);
		
		if(cartaoOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cartao cartao = cartaoOpt.get();
		
		List<String> list = cartao.getBiomatrias()
			.stream()
			.map(b -> b.getFingerprint())
			.collect(Collectors.toList());

		
		return ResponseEntity.ok(list);
	}
	
}
