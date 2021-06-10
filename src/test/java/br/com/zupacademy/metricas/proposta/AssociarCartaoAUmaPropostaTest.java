package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
		
		
		Proposta elegivel1 = criaPropostaPersisteEConfiguraOMock("elegivel1@gmail.com",Estado.ELEGIVEL,"123");
		Proposta naoElegivel = criaPropostaPersisteEConfiguraOMock("naoelegivel@gmail.com",Estado.NAO_ELEGIVEL,"1234");
		Proposta elegivel2 = criaPropostaPersisteEConfiguraOMock("elegivel2@gmail.com",Estado.ELEGIVEL,"12345");

		associarCartao.associar();
		
		verify(apiDeCartao,times(1)).buscaCartao(elegivel1.getId());
		verify(apiDeCartao,times(1)).buscaCartao(elegivel2.getId());
		
		assertNotNull(elegivel1.getCartao());
		assertNull(naoElegivel.getCartao());
		assertNotNull(elegivel2.getCartao());
	}

	private Proposta criaPropostaPersisteEConfiguraOMock(String email, Estado estado,String codigoCartao) {
		Proposta proposta = new Proposta("768.250.480-31", "gabriel@gmail.com", "Gabriel", "vila nova", BigDecimal.TEN);
		proposta.setEstado(estado);
		
		Proposta elegivel1 = proposta;
		propostaReposirory.save(elegivel1);
		
		CartaoResponse cartaoResponse = new CartaoResponse();
		cartaoResponse.setId(codigoCartao);
		Mockito.when(apiDeCartao.buscaCartao(elegivel1.getId())).thenReturn(cartaoResponse);
		return elegivel1;
	}
	
	

}
