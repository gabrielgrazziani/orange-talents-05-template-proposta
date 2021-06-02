package br.com.zupacademy.metricas.proposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.zupacademy.metricas.geral.CpfOuCnpj;

public class PropostaForm {
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
	
	public PropostaForm(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String enderaco, @NotNull @PositiveOrZero BigDecimal salario) {
		super();
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.enderaco = enderaco;
		this.salario = salario;
	}

	public Proposta map() {
		return new Proposta(documento, email, nome, enderaco, salario);
	}

	
}
