package br.com.zupacademy.metricas.proposta;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartao")
public class BloqueioCataoController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@PutMapping("/{idCartao}/bloquear")
	public ResponseEntity<?> bloquear(@PathVariable String idCartao,Principal principal,HttpServletRequest request) {
		Optional<Cartao> cartaoOpt = cartaoRepository.findByCodigoCartao(idCartao);
		if(cartaoOpt.isEmpty()) {
			return ResponseEntity.notFound().build();			
		}
		
		Cartao cartao = cartaoOpt.get();
		String idDeQueFezOBloqueio = principal.getName();
		String ipDeQueFezOBloqueio = request.getRemoteAddr();
		Bloqueio bloqueio = new Bloqueio(idDeQueFezOBloqueio,ipDeQueFezOBloqueio,cartao);
		
		boolean bloqueado = cartao.tentarBloquear(bloqueio);
		
		if(!bloqueado){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		cartaoRepository.save(cartao);
		return ResponseEntity.ok().build();
	}
}
