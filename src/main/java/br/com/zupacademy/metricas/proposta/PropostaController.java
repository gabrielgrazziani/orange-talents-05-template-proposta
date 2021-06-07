package br.com.zupacademy.metricas.proposta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import br.com.zupacademy.metricas.config.feign.SolicitacaoComRestricao;
import br.com.zupacademy.metricas.geral.ApiDeAnalise;
import br.com.zupacademy.metricas.geral.SolicitacaoRequest;
import br.com.zupacademy.metricas.geral.SolicitacaoResponse;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);
	
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
			SolicitacaoResponse solicitacao = analise.solicitacao(new SolicitacaoRequest(proposta));
			Assert.state(solicitacao.getResultadoSolicitacao().equals("SEM_RESTRICAO"),"O estado Resultado da Solicitacao esperado e SEM_RESTRICAO");
			estado = Estado.ELEGIVEL;
		} catch (SolicitacaoComRestricao e) {
			estado = Estado.NAO_ELEGIVEL;			
		}
		proposta.setEstado(estado);
		
		String documentoOfuscado =  proposta.getDocumento().substring(0, 3);
		logger.info("Proposta documento={}.***.***.***-** estado={} criado com sucesso!",documentoOfuscado,estado);
		
		UriComponents uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{id}").buildAndExpand(proposta.getId());
		
		return ResponseEntity.created(uri.toUri()).build();
	}
	
}
