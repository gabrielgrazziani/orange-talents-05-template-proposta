package br.com.zupacademy.metricas.proposta;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposta")
public class BuscaPropostaController {

	@PersistenceContext
	private EntityManager entityManager;
	
	@GetMapping("/{idProposta}")
	public ResponseEntity<PropostaDto> busca(@PathVariable Long idProposta) {
		Proposta proposta = entityManager.find(Proposta.class, idProposta);
		
		if(Objects.isNull(proposta)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new PropostaDto(proposta));
	}
}
