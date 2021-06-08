package br.com.zupacademy.metricas.proposta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String cartao;
	
	@SuppressWarnings("unused")
	@Deprecated
	private Cartao() {
	}

	public Cartao(@NotBlank String cartao) {
		this.cartao = cartao;
	}
}
