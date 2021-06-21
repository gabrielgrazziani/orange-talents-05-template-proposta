package br.com.zupacademy.metricas.proposta;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zupacademy.metricas.geral.ApiDeCartao;
import br.com.zupacademy.metricas.geral.CartaoResponse;
import feign.FeignException;
import io.opentracing.Tracer;

@Component
@ConditionalOnProperty(name = "scheduling.enabled")
public class AssociarCartaoAUmaProposta {
	
	private final Logger logger = LoggerFactory.getLogger(AssociarCartaoAUmaProposta.class);
	
	@Autowired
	private PropostaReposirory propostaReposirory;
	@Autowired
	private ApiDeCartao apiDeCartao;
	@Autowired
	private Tracer tracer;
	
    public AssociarCartaoAUmaProposta(PropostaReposirory propostaReposirory, ApiDeCartao apiDeCartao,Tracer tracer) {
		this.propostaReposirory = propostaReposirory;
		this.apiDeCartao = apiDeCartao;
		this.tracer = tracer;
	}

	@Scheduled(fixedDelayString = "${periodicidade.associar-cartao-a-uma-proposta}")
    public void associar() {
    	List<Proposta> propostas = propostaReposirory.findByEstadoAndCartaoIsNull(Estado.ELEGIVEL);
    	tracer.activeSpan().setTag("possui.propostas", !propostas.isEmpty());
    	propostas.forEach(p -> {
    		associar(p);
    	});
    }

	private void associar(Proposta proposta) {
		try {
    		CartaoResponse cartao = apiDeCartao.buscaCartao(proposta.getId());
    		proposta.setCartao(cartao.map());
    		propostaReposirory.save(proposta);
    		logger.info("cartão da proposta {} criado",proposta.getId());
		} catch (FeignException e) {
			logger.info("falha ao criar cartão para proposta {} ",proposta.getId());
		}
	}

}