package br.com.zupacademy.metricas.cartao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	Optional<Cartao> findByCodigoCartao(String codigoCartao);
}
