package br.com.zupacademy.metricas.geral.api;

import br.com.zupacademy.metricas.proposta.Proposta;

public class SolicitacaoRequest {
	
	private Long idProposta;
	private String nome;
	private String documento;
	
	public SolicitacaoRequest(Proposta proposta) {
		this.idProposta = proposta.getId();
		this.nome = proposta.getNome();
		this.documento = proposta.getDocumento();
	}

	public Long getIdProposta() {
		return idProposta;
	}

	public String getNome() {
		return nome;
	}

	public String getDocumento() {
		return documento;
	}
}
