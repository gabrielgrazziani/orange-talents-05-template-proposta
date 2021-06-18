package br.com.zupacademy.metricas.proposta;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AvisoViagemForm {
	
	@NotBlank
	@JsonProperty
	private String destino;

	@NotNull
	@Future
	@JsonProperty
	private LocalDate dataTerminoViagem;
	
	public AvisoViagemForm(@NotBlank String destino, @Future @NotNull LocalDate dataTerminoViagem) {
		this.destino = destino;
		this.dataTerminoViagem = dataTerminoViagem;
	}

	public AvisoViagem map(Cartao cartao,HttpServletRequest request) {
		return new AvisoViagem(destino,dataTerminoViagem,cartao,request.getRemoteAddr(),request.getHeader("User-Agent"));
	}
	
	public LocalDate getDataTerminoViagem() {
		return dataTerminoViagem;
	}
	
	public String getDestino() {
		return destino;
	}

}
