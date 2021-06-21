package br.com.zupacademy.metricas.biometria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.metricas.cartao.Cartao;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Lob
	private String fingerprint;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cartao_id")
	private Cartao cartao;
	
	@SuppressWarnings("unused")
	@Deprecated
	private Biometria() {
	}
	
	public Biometria(@NotNull String fingerprint,@NotNull Cartao cartao) {
		this.fingerprint = fingerprint;
		this.cartao = cartao;
	}
	
	public String getFingerprint() {
		return fingerprint;
	}

	public Long getId() {
		return this.id;
	}

}
