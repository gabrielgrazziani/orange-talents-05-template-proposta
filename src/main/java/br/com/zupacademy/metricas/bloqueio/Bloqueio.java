package br.com.zupacademy.metricas.bloqueio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import br.com.zupacademy.metricas.cartao.Cartao;

@Entity
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String userAgent;
	@NotBlank
	private String ipDeQueFezOBloqueio;
	@ManyToOne
	private Cartao cartao;
	@PastOrPresent
	private LocalDateTime instanteCriacao;

	@SuppressWarnings("unused")
	@Deprecated
	private Bloqueio() {
	}
	
	public Bloqueio(String userAgent, String ipDeQueFezOBloqueio, Cartao cartao) {
		this.userAgent = userAgent;
		this.ipDeQueFezOBloqueio = ipDeQueFezOBloqueio;
		this.cartao = cartao;
		instanteCriacao = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartao == null) ? 0 : cartao.hashCode());
		result = prime * result + ((userAgent == null) ? 0 : userAgent.hashCode());
		result = prime * result + ((instanteCriacao == null) ? 0 : instanteCriacao.hashCode());
		result = prime * result + ((ipDeQueFezOBloqueio == null) ? 0 : ipDeQueFezOBloqueio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bloqueio other = (Bloqueio) obj;
		if (cartao == null) {
			if (other.cartao != null)
				return false;
		} else if (!cartao.equals(other.cartao))
			return false;
		if (userAgent == null) {
			if (other.userAgent != null)
				return false;
		} else if (!userAgent.equals(other.userAgent))
			return false;
		if (instanteCriacao == null) {
			if (other.instanteCriacao != null)
				return false;
		} else if (!instanteCriacao.equals(other.instanteCriacao))
			return false;
		if (ipDeQueFezOBloqueio == null) {
			if (other.ipDeQueFezOBloqueio != null)
				return false;
		} else if (!ipDeQueFezOBloqueio.equals(other.ipDeQueFezOBloqueio))
			return false;
		return true;
	}
	
}
