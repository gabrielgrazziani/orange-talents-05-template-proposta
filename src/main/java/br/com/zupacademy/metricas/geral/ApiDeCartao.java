package br.com.zupacademy.metricas.geral;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonProperty;

@FeignClient(name = "cartao",url = "${cartao.host}")
public interface ApiDeCartao {
	
	@RequestMapping(value = "/cartoes",method = RequestMethod.GET)
	CartaoResponse buscaCartao(@RequestParam Long idProposta);

	
	@RequestMapping(value = "/cartoes/{idCartao}/bloqueios",method = RequestMethod.POST)
	ResultadoBloqueio bloqueiaCartao(@PathVariable String idCartao,@RequestBody SolicitacaoBloqueio solicitacaoBloqueio);

	class SolicitacaoBloqueio{
		
		@JsonProperty
		String sistemaResponsavel;

		public SolicitacaoBloqueio(String sistemaResponsavel) {
			this.sistemaResponsavel = sistemaResponsavel;
		}
		
	}
	
	class ResultadoBloqueio{
		@JsonProperty
		Resultado resultado;
		
		public ResultadoBloqueio(Resultado resultado) {
			this.resultado = resultado;
		}
		
		public Resultado getResultado() {
			return resultado;
		}
	}
	
	enum Resultado{
		BLOQUEADO, FALHA;
	}
}
