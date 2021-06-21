package br.com.zupacademy.metricas.aviso_viagem;

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

import br.com.zupacademy.metricas.cartao.Cartao;
import br.com.zupacademy.metricas.cartao.CartaoRepository;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.SolicitacaoAvisoViagem;
import feign.FeignException.FeignClientException;

@RestController
@RequestMapping("/cartao")
public class AvisoViagemController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ApiDeCartao apiDeCartao;

	@PutMapping("{idCartao}/aviso_viagem")
	public ResponseEntity<?> aviso(@PathVariable String idCartao,
			@Valid @RequestBody AvisoViagemForm form, HttpServletRequest request) {
		Optional<Cartao> optional = cartaoRepository.findByCodigoCartao(idCartao);
		if (optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cartao cartao = optional.get();
		
		try {
			apiDeCartao.avisoViagem(idCartao, new SolicitacaoAvisoViagem(form));
		} catch (FeignClientException e) {
			return ResponseEntity.status(e.status()).build();
		}
		
		AvisoViagem avisoViagem = form.map(cartao, request);
		cartao.novoAvisoDeViagem(avisoViagem);
		cartaoRepository.save(cartao);
		
		return ResponseEntity.ok().build();
	}
}
