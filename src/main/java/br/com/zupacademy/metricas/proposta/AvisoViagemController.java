package br.com.zupacademy.metricas.proposta;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartao")
public class AvisoViagemController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@PutMapping("{idCartao}/aviso_viagem")
	public ResponseEntity<Object> aviso(@PathVariable String idCartao,
			@Valid @RequestBody AvisoViagemForm avisoViagemFrom, HttpServletRequest request) {
		Optional<Cartao> optional = cartaoRepository.findByCodigoCartao(idCartao);
		if (optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cartao cartao = optional.get();
		
		AvisoViagem avisoViagem = avisoViagemFrom.map(cartao, request);
		cartao.novoAvisoDeViagem(avisoViagem);
		cartaoRepository.save(cartao);
		
		return ResponseEntity.ok().build();
	}
}
