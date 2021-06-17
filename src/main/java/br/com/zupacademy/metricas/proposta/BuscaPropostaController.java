package br.com.zupacademy.metricas.proposta;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.metricas.config.health_check.MetricasProposta;

@RestController
@RequestMapping("/proposta")
public class BuscaPropostaController {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MetricasProposta metricasProposta;
	
	@GetMapping("/{idProposta}")
	public ResponseEntity<PropostaDto> busca(@PathVariable Long idProposta) {
		Proposta proposta = metricasProposta.calcularTempo(() -> 
			entityManager.find(Proposta.class, idProposta)
		);
		
		if(Objects.isNull(proposta)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new PropostaDto(proposta));
	}
}
