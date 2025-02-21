package br.com.zupacademy.metricas.carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.metricas.cartao.Cartao;

public class CarteiraForm {
	
	@Email
	@NotBlank
	private String email;
	@NotNull
	private TipoCarteiraForm tipoCarteira;
	
	public CarteiraForm(String email, TipoCarteiraForm tipoCarteira) {
		super();
		this.email = email;
		this.tipoCarteira = tipoCarteira;
	}

	public Carteira map(Cartao cartao) {
		return new Carteira(email,cartao,tipoCarteira.map());
	}
	
	public String getEmail() {
		return email;
	}
	
	public TipoCarteiraForm getTipoCarteira() {
		return tipoCarteira;
	}
}
