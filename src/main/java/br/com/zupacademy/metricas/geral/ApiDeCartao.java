package br.com.zupacademy.metricas.geral;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zupacademy.metricas.proposta.AvisoViagemForm;
import br.com.zupacademy.metricas.proposta.CarteiraForm;

@FeignClient(name = "cartao",url = "${cartao.host}")
public interface ApiDeCartao {
	
	@RequestMapping(value = "/cartoes",method = RequestMethod.GET)
	CartaoResponse buscaCartao(@RequestParam Long idProposta);

	
	@RequestMapping(value = "/cartoes/{idCartao}/bloqueios",method = RequestMethod.POST)
	ResultadoBloqueio bloqueiaCartao(@PathVariable String idCartao,@RequestBody SolicitacaoBloqueio solicitacaoBloqueio);

	@RequestMapping(value = "/cartoes/{idCartao}/avisos",method = RequestMethod.POST)
	ResultadoAvisoViagem avisoViagem(@PathVariable String idCartao,SolicitacaoAvisoViagem solicitacaoAvisoViagem);

	@RequestMapping(value = "/cartoes/{idCartao}/carteiras",method = RequestMethod.POST)
	ResultadoCarteira novaCarteira(@PathVariable String idCartao,SolicitacaoCarteira carteira);
	
	class SolicitacaoCarteira{
		@JsonProperty
		private String email;
		@JsonProperty
		private String carteira;

		public SolicitacaoCarteira(CarteiraForm form) {
			this.email = form.getEmail();
			this.carteira = form.getTipoCarteira().name();
		}
	}
	
	class ResultadoCarteira{
		@JsonProperty
		private String id;
		@JsonProperty
		private ResultadoCarteiraEnum resultado;

		public ResultadoCarteira(String id,ResultadoCarteiraEnum resultado) {
			this.id = id;
			this.resultado = resultado;
		}
	}
	
	enum ResultadoCarteiraEnum{
		ASSOCIADA, FALHA;
	}

	class SolicitacaoAvisoViagem{
		
		@JsonProperty
		private String destino;

		@JsonProperty
		private LocalDate validoAte;
		
		public SolicitacaoAvisoViagem(AvisoViagemForm avisoViagem) {
			this.validoAte = avisoViagem.getDataTerminoViagem();
			this.destino = avisoViagem.getDestino();
		}
		
	}
	
	
	class SolicitacaoBloqueio{
		
		@JsonProperty
		String sistemaResponsavel;

		public SolicitacaoBloqueio(String sistemaResponsavel) {
			this.sistemaResponsavel = sistemaResponsavel;
		}
		
	}
	
	class ResultadoAvisoViagem{
		@JsonProperty
		ResultadoAvisoViagemEnum resultado;
		
		@JsonCreator
		public ResultadoAvisoViagem(ResultadoAvisoViagemEnum resultado) {
			this.resultado = resultado;
		}
		
		public ResultadoAvisoViagemEnum getResultado() {
			return resultado;
		}
	}
	
	class ResultadoBloqueio{
		@JsonProperty
		ResultadoBloqueioEnum resultado;
		
		@JsonCreator
		public ResultadoBloqueio(ResultadoBloqueioEnum resultado) {
			this.resultado = resultado;
		}
		
		public ResultadoBloqueioEnum getResultado() {
			return resultado;
		}
	}
	
	enum ResultadoAvisoViagemEnum{
		CRIADO,FALHA;
	}
	
	enum ResultadoBloqueioEnum{
		BLOQUEADO, FALHA;
	}


}
