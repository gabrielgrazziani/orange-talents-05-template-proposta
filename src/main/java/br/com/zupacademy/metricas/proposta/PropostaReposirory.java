package br.com.zupacademy.metricas.proposta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PropostaReposirory extends JpaRepository<Proposta, Long>{

	@Query("select p from Proposta p where p.estado = br.com.zupacademy.metricas.proposta.Estado.ELEGIVEL and p.numeroCartao = null")
	List<Proposta> propostasElegiveisSemNumeroDeCartao();

}
