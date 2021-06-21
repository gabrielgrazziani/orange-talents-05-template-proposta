package br.com.zupacademy.metricas.aviso_viagem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import com.sun.istack.NotNull;

import br.com.zupacademy.metricas.cartao.Cartao;

@Entity
public class AvisoViagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String destino;
	@NotNull
	private LocalDate dataTerminoViagem;
	@NotNull
	@ManyToOne
	private Cartao cartao;
	private String ipDeQueFezORegisto;
	private String userAgent;

	@PastOrPresent
	private LocalDateTime instanteRegistroAvisoViagem;

	@SuppressWarnings("unused")
	@Deprecated
	private AvisoViagem() {
	}

	public AvisoViagem(@NotBlank String destino,@NotNull LocalDate dataTerminoViagem,@NotNull Cartao cartao, String ipDeQueFezORegisto,
			String userAgent) {
				this.destino = destino;
				this.dataTerminoViagem = dataTerminoViagem;
				this.cartao = cartao;
				this.ipDeQueFezORegisto = ipDeQueFezORegisto;
				this.userAgent = userAgent;
				this.instanteRegistroAvisoViagem = LocalDateTime.now();
	}

}
