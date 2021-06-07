package br.com.zupacademy.metricas.proposta;

public class PropostaDto {

	private Long id;
	private String numeroCartao;
	private String documento;

	public PropostaDto(Proposta proposta) {
		documento = proposta.getDocumento();
		id = proposta.getId();
		numeroCartao = proposta.getNumeroCartao();
	}

	public Long getId() {
		return id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getDocumento() {
		return documento;
	}
	
}
