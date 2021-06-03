package br.com.zupacademy.metricas.geral;

public class SolicitacaoResponse {
	
	private String idProposta;
	private String nome;
	private String documento;
	private String resultadoSolicitacao;
	
	public SolicitacaoResponse() {
	}

	public String getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}

	public void setIdProposta(String idProposta) {
		this.idProposta = idProposta;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public void setResultadoSolicitacao(String resultadoSolicitacao) {
		this.resultadoSolicitacao = resultadoSolicitacao;
	}
	
}
