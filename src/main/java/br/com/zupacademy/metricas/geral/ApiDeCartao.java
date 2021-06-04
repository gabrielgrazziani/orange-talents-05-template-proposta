package br.com.zupacademy.metricas.geral;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao",url = "${cartao.host}")
public interface ApiDeCartao {
	
	@RequestMapping(value = "/cartoes",method = RequestMethod.GET)
	CartaoResponse buscaCartao(@RequestParam Long idProposta);

}
