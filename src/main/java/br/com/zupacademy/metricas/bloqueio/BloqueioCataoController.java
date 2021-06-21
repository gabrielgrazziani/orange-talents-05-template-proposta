package br.com.zupacademy.metricas.bloqueio;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.metricas.cartao.Cartao;
import br.com.zupacademy.metricas.cartao.CartaoRepository;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.SolicitacaoBloqueio;
import feign.FeignException.FeignClientException;

@RestController
@RequestMapping("/cartao")
public class BloqueioCataoController {
	
	@Autowired
	private ApiDeCartao apiDeCartao;

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@PutMapping("/{idCartao}/bloquear")
	public ResponseEntity<?> bloquear(@PathVariable String idCartao,HttpServletRequest request) {
		Optional<Cartao> cartaoOpt = cartaoRepository.findByCodigoCartao(idCartao);
		if(cartaoOpt.isEmpty()) {
			return ResponseEntity.notFound().build();			
		}
		Cartao cartao = cartaoOpt.get();
		
		Bloqueio bloqueio = new Bloqueio(request.getHeader("User-Agent"),request.getRemoteAddr(),cartao);
		
		boolean bloqueado = cartao.tentarBloquear(bloqueio);
		
		if(!bloqueado){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		try {
			apiDeCartao.bloqueiaCartao(idCartao,new SolicitacaoBloqueio("Sistema de Propostas"));
		} catch (FeignClientException e) {
			return ResponseEntity.status(e.status()).build();
		}
	
		cartaoRepository.save(cartao);
		return ResponseEntity.ok().build();
	}
	
}
