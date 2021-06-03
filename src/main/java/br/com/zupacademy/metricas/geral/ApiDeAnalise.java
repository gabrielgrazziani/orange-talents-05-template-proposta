package br.com.zupacademy.metricas.geral;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "analise",url = "http://localhost:9999/api")
public interface ApiDeAnalise {

	@RequestMapping(method = RequestMethod.POST,value = "/solicitacao")
	SolicitacaoResponse solicitacao(SolicitacaoRequest request);
}
