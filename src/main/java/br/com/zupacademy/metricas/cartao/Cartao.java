package br.com.zupacademy.metricas.cartao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import br.com.zupacademy.metricas.aviso_viagem.AvisoViagem;
import br.com.zupacademy.metricas.biometria.Biometria;
import br.com.zupacademy.metricas.bloqueio.Bloqueio;
import br.com.zupacademy.metricas.carteira.Carteira;
import br.com.zupacademy.metricas.carteira.TipoCarteira;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String codigoCartao;
	
	private boolean bloqueado;
	
	@OneToMany(mappedBy = "cartao",cascade = {CascadeType.MERGE})
	private List<Bloqueio> bloqueios = new ArrayList<>();
	
	@OneToMany(mappedBy = "cartao",cascade = {CascadeType.MERGE})
	private List<Biometria> biomatrias = new ArrayList<>();

	@OneToMany(mappedBy = "cartao",cascade = {CascadeType.MERGE})
	private List<AvisoViagem> avisosViagem = new ArrayList<>();

	@OneToMany(mappedBy = "cartao",cascade = {CascadeType.MERGE})
	private List<Carteira> carteiras = new ArrayList<>();
	
	@SuppressWarnings("unused")
	@Deprecated
	private Cartao() {
	}

	public Cartao(@NotBlank String codigoCartao) {
		this.codigoCartao = codigoCartao;
		this.bloqueado = false;
	}
	
	public void novaBiometrias(Biometria biomatria) {
		biomatrias.add(biomatria);
	}
	
	public Biometria ultimaBiometria() {
		return biomatrias.get(biomatrias.size()-1);
	}
	
	public List<Biometria> getBiomatrias() {
		return biomatrias;
	}

	public boolean tentarBloquear(Bloqueio bloqueio) {
		if(bloqueado) return false;
		
		bloqueios.add(bloqueio);
		bloqueado = true;
		
		return true;
	}

	public boolean estaBloqueado() {
		return this.bloqueado;
	}

	public List<Bloqueio> getBloqueios() {
		return this.bloqueios;
	}
	
	public boolean novaCarteira(Carteira carteira) {
		if(jaTemEsseTipoDeCarteira(carteira.getTipoCarteira())) return false;
		this.carteiras.add(carteira);
		return true;
	}
	
	private boolean jaTemEsseTipoDeCarteira(TipoCarteira tipoCarteira) {
		return this.carteiras.stream()
			.filter(e -> e.getTipoCarteira() == tipoCarteira)
			.count() > 0;
	}

	public void novoAvisoDeViagem(AvisoViagem avisoViagem) {
		avisosViagem.add(avisoViagem);
	}

	public List<AvisoViagem> getAvisosViagem() {
		return this.avisosViagem;
	}
}
