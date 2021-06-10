package br.com.zupacademy.metricas.proposta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PropostaReposirory extends JpaRepository<Proposta, Long>{

	List<Proposta> findByEstadoAndCartaoIsNull(Estado estado);

}
