package br.com.zupacademy.metricas.geral;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zupacademy.metricas.config.feign.ApiDeAnaliseConfig;

@FeignClient(name = "analise",url = "${analise.host}",configuration = ApiDeAnaliseConfig.class)
public interface ApiDeAnalise {

	@RequestMapping(method = RequestMethod.POST,value = "/solicitacao")
	SolicitacaoResponse solicitacao(SolicitacaoRequest request);
}
