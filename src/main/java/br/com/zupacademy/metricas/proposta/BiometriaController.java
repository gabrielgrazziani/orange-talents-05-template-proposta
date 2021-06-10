package br.com.zupacademy.metricas.proposta;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class BiometriaController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@PostMapping("cartao/{codigoCartao}/biometria")
	@Transactional
	public ResponseEntity<?> novaBiometria(@PathVariable String codigoCartao,
			@Valid @RequestBody BiometriaForm form) {
		Optional<Cartao> cartaoOpt = cartaoRepository.findByCodigoCartao(codigoCartao);
		
		if(cartaoOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cartao cartao = cartaoOpt.get();
		
		Biometria biomatria = form.map(cartao);
		cartao.novaBiomatrias(biomatria);
		cartao = cartaoRepository.saveAndFlush(cartao);
		
		//O id esta vindo nulo
//		Assert.state(biomatria.getId() != null,"Biometria deveria ter um id neste ponto");
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
