package br.com.zupacademy.metricas.proposta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zupacademy.metricas.geral.ApiDeCartao;
import br.com.zupacademy.metricas.geral.CartaoResponse;
import feign.FeignException;

@Component
public class AssociarCartaoAUmaProposta {
	
	@Autowired
	private PropostaReposirory propostaReposirory;
	@Autowired
	private ApiDeCartao apiDeCartao;
	
    public AssociarCartaoAUmaProposta(PropostaReposirory propostaReposirory, ApiDeCartao apiDeCartao) {
		super();
		this.propostaReposirory = propostaReposirory;
		this.apiDeCartao = apiDeCartao;
	}

	@Scheduled(fixedDelayString = "${periodicidade.associar-cartao-a-uma-proposta}")
    public void associar() {
    	List<Proposta> propostas = propostaReposirory.propostasElegiveisSemCartao();
    	propostas.forEach(p -> {
    		associar(p);
    	});
    }

	private void associar(Proposta proposta) {
		try {
    		CartaoResponse cartao = apiDeCartao.buscaCartao(proposta.getId());
    		proposta.setCartao(cartao.map());
    		propostaReposirory.save(proposta);
		} catch (FeignException e) {
		}
	}

}