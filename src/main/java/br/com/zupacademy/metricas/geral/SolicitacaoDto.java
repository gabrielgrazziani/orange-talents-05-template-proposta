package br.com.zupacademy.metricas.geral;

import br.com.zupacademy.metricas.proposta.Proposta;

public class SolicitacaoDto {
	
	private Long idProposta;
	private String nome;
	private String documento;
	
	public SolicitacaoDto(Proposta proposta) {
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
