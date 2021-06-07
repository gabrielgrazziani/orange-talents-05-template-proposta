package br.com.zupacademy.metricas.proposta;

public class PropostaDto {

	private Long id;
	private Estado estado;

	public PropostaDto(Proposta proposta) {
		id = proposta.getId();
		estado = proposta.getEstado();
	}

	public Long getId() {
		return id;
	}

	public Estado getEstado() {
		return estado;
	}
	
}
