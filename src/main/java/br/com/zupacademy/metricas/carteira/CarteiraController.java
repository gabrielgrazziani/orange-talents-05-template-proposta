package br.com.zupacademy.metricas.carteira;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.zupacademy.metricas.cartao.Cartao;
import br.com.zupacademy.metricas.cartao.CartaoRepository;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.SolicitacaoCarteira;
import feign.FeignException.FeignClientException;

@RestController
@RequestMapping("/cartao")
public class CarteiraController {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ApiDeCartao apiDeCartao;
	
	@PostMapping("/{idCartao}/carteira")
	public ResponseEntity<?> post(@PathVariable String idCartao,@Valid @RequestBody CarteiraForm form){
		Optional<Cartao> optional = cartaoRepository.findByCodigoCartao(idCartao);
		if (optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cartao cartao = optional.get();
		
		Carteira carteira = form.map(cartao);
		boolean adicionada = cartao.novaCarteira(carteira);
		if(!adicionada) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		try {
			apiDeCartao.novaCarteira(idCartao,new SolicitacaoCarteira(form));
		} catch (FeignClientException e) {
			return ResponseEntity.status(e.status()).build();
		}
		
		cartaoRepository.save(cartao);
		return ResponseEntity.created(uri(carteira)).build();
	}

	private URI uri(Carteira carteira) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}").build(carteira.getId());
	}

}
