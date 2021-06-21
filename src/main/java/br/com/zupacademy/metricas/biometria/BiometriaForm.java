package br.com.zupacademy.metricas.biometria;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import br.com.zupacademy.metricas.cartao.Cartao;
import br.com.zupacademy.metricas.geral.Base64;

public class BiometriaForm {

	@NotNull
	@Base64
	private String fingerprint;
	
	@JsonCreator(mode = Mode.PROPERTIES)
	public BiometriaForm(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Biometria map(Cartao cartao) {
		return new Biometria(fingerprint,cartao);
	}
}
