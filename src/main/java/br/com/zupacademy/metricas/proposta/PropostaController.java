package br.com.zupacademy.metricas.proposta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import br.com.zupacademy.metricas.geral.ApiDeAnalise;
import br.com.zupacademy.metricas.geral.SolicitacaoRequest;
import feign.FeignException.UnprocessableEntity;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private ApiDeAnalise analise;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cria(@Valid @RequestBody PropostaForm form){
		Proposta proposta = form.map();
		entityManager.persist(proposta);
		
		Estado estado;
		try {
			analise.solicitacao(new SolicitacaoRequest(proposta));
			estado = Estado.ELEGIVEL;
		} catch (UnprocessableEntity e) {
			estado = Estado.NAO_ELEGIVEL;			
		}
		proposta.setEstado(estado);
		
		UriComponents uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{id}").buildAndExpand(proposta.getId());
		
		return ResponseEntity.created(uri.toUri()).build();
	}
	
}
