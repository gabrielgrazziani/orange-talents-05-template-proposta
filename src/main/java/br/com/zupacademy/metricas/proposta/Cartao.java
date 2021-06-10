package br.com.zupacademy.metricas.proposta;

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

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String codigoCartao;
	
	@OneToMany(mappedBy = "cartao",cascade = {CascadeType.MERGE})
	private List<Biometria> biomatrias = new ArrayList<>();
	
	@SuppressWarnings("unused")
	@Deprecated
	private Cartao() {
	}

	public Cartao(@NotBlank String codigoCartao) {
		this.codigoCartao = codigoCartao;
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
}
