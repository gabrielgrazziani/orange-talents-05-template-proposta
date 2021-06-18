package br.com.zupacademy.metricas.proposta;

public enum TipoCarteiraForm {
	PAY_PAL(TipoCarteira.PAY_PAL);

	private TipoCarteira tipoCarteira;

	TipoCarteiraForm(TipoCarteira tipoCarteira) {
		this.tipoCarteira = tipoCarteira;
	}
	
	public TipoCarteira map() {
		return this.tipoCarteira;
	}
}
