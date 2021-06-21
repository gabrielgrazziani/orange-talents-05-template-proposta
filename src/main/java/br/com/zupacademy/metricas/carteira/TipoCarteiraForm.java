package br.com.zupacademy.metricas.carteira;

public enum TipoCarteiraForm {
	PAY_PAL(TipoCarteira.PAY_PAL),
	SAMSUNG_PAY(TipoCarteira.SAMSUNG_PAY);

	private TipoCarteira tipoCarteira;

	TipoCarteiraForm(TipoCarteira tipoCarteira) {
		this.tipoCarteira = tipoCarteira;
	}
	
	public TipoCarteira map() {
		return this.tipoCarteira;
	}
}
