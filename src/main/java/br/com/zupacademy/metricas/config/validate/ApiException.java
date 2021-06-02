package br.com.zupacademy.metricas.config.validate;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String campo;
	private String mensagem;
	
	private HttpStatus status;

	public ApiException(String campo, String mensagem, HttpStatus status) {
		super(mensagem);
		this.campo = campo;
		this.mensagem = mensagem;
		this.status = status;
	}

	public ApiException(String mensagem, HttpStatus status) {
		super(mensagem);
		this.mensagem = mensagem;
		this.status = status;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}
