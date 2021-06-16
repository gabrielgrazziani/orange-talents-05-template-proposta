package br.com.zupacademy.metricas.proposta;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class AvisoViagemForm {
	
	@NotBlank
	private String destino;

	@NotNull
	@Future
	private LocalDate dataTerminoViagem;
	
	public AvisoViagemForm(@NotBlank String destino, @Future @NotNull LocalDate dataTerminoViagem) {
		this.destino = destino;
		this.dataTerminoViagem = dataTerminoViagem;
	}

	public AvisoViagem map(Cartao cartao,HttpServletRequest request) {
		return new AvisoViagem(destino,dataTerminoViagem,cartao,request.getRemoteAddr(),request.getHeader("User-Agent"));
	}

}
