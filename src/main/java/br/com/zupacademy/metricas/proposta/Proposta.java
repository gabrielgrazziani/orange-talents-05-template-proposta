package br.com.zupacademy.metricas.proposta;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.zupacademy.metricas.geral.CpfOuCnpj;

@Entity
public class Proposta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@CpfOuCnpj
	private String documento;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String enderaco;
	@NotNull
	@PositiveOrZero
	private BigDecimal salario;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	private String numeroCartao;
	
	public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String enderaco, @NotNull @PositiveOrZero BigDecimal salario) {
		super();
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.enderaco = enderaco;
		this.salario = salario;
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private Proposta() {
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return this.nome;
	}

	public String getDocumento() {
		return this.documento;
	}
	
	public String getNumeroCartao() {
		return numeroCartao;
	}
	
}
