package br.com.zupacademy.metricas.proposta;

public class PropostaDto {

	private Long id;
	private String documento;
	private Estado estado;

	public PropostaDto(Proposta proposta) {
		id = proposta.getId();
		estado = proposta.getEstado();
		documento = proposta.getDocumento();
	}

	public Long getId() {
		return id;
	}

	public Estado getEstado() {
		return estado;
	}
	
	public String getDocumento() {
		return documento;
	}
	
}
