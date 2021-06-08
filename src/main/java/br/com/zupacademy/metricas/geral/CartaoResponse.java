package br.com.zupacademy.metricas.geral;

import br.com.zupacademy.metricas.proposta.Cartao;

public class CartaoResponse {
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cartao map() {
		return new Cartao(id);
	}

}
