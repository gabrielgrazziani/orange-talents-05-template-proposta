package br.com.zupacademy.metricas.geral;

public class SolicitacaoResponse {
	
	private String resultadoSolicitacao;
	
	public SolicitacaoResponse(String resultadoSolicitacao) {
		this.resultadoSolicitacao = resultadoSolicitacao;
	}
	
	public SolicitacaoResponse() {
	}

	public String getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}

	public void setResultadoSolicitacao(String resultadoSolicitacao) {
		this.resultadoSolicitacao = resultadoSolicitacao;
	}
	
}
