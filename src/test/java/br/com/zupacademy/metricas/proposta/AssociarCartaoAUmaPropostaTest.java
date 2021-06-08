package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.com.zupacademy.metricas.geral.ApiDeCartao;
import br.com.zupacademy.metricas.geral.CartaoResponse;

@SpringBootTest
@AutoConfigureDataJpa
@ActiveProfiles("test")
class AssociarCartaoAUmaPropostaTest {
	
	@Autowired
	private PropostaReposirory propostaReposirory;
	
	@MockBean
	private ApiDeCartao apiDeCartao;

	@Test
	@Transactional
	void deveAssociarAsPropostaElegiveis() {
		var associarCartao = new AssociarCartaoAUmaProposta(propostaReposirory,apiDeCartao);
		
		CartaoResponse cartaoResponse = new CartaoResponse();
		cartaoResponse.setId("78943789");
		Mockito.when(apiDeCartao.buscaCartao(anyLong())).thenReturn(cartaoResponse);
		
		Proposta elegivel1 = novaProposta("elegivel1@gmail.com",Estado.ELEGIVEL);
		Proposta naoElegivel = novaProposta("naoelegivel@gmail.com",Estado.NAO_ELEGIVEL);
		Proposta elegivel2 = novaProposta("elegivel2@gmail.com",Estado.ELEGIVEL);

		propostaReposirory.save(elegivel1);
		propostaReposirory.save(naoElegivel);
		propostaReposirory.save(elegivel2);
		
		associarCartao.associar();
		
		verify(apiDeCartao,times(2)).buscaCartao(anyLong());
		
		assertNotNull(elegivel1.getCartao());
		assertNull(naoElegivel.getCartao());
		assertNotNull(elegivel2.getCartao());
	}

	private Proposta novaProposta(String email, Estado estado) {
		Proposta proposta = new Proposta("768.250.480-31", "gabriel@gmail.com", "Gabriel", "vila nova", BigDecimal.TEN);
		proposta.setEstado(estado);
		return proposta;
	}
	
	

}
