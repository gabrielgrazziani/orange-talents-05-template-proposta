package br.com.zupacademy.metricas.carteira;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.metricas.cartao.Cartao;

@Entity
public class Carteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@NotBlank
	private String email;
	@NotNull
	@ManyToOne
	private Cartao cartao;
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoCarteira tipoCarteira;

	@SuppressWarnings("unused")
	@Deprecated
	private Carteira() {
	}
	
	public Carteira(String email, Cartao cartao, TipoCarteira tipoCarteira) {
		this.email = email;
		this.cartao = cartao;
		this.tipoCarteira = tipoCarteira;
	}
	
	public TipoCarteira getTipoCarteira() {
		return tipoCarteira;
	}

	public Long getId() {
		return this.id;
	}

}
