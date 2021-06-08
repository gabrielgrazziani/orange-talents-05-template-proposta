package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zupacademy.metricas.config.feign.SolicitacaoComRestricao;
import br.com.zupacademy.metricas.geral.ApiDeAnalise;
import br.com.zupacademy.metricas.geral.SolicitacaoResponse;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PropostaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ApiDeAnalise apiDeAnalise;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void deveCriarUmaPropostaELEGIVEL() throws Exception {
		PropostaForm form = new PropostaForm("768.250.480-31", "gabriel@gmail.com", "Gabriel", "vila nova", BigDecimal.TEN);
		String jsom = jsom(form);
		
		SolicitacaoResponse solicitacaoResponse = new SolicitacaoResponse("SEM_RESTRICAO");
		Mockito.when(apiDeAnalise.solicitacao(any())).thenReturn(solicitacaoResponse);
		
		mockMvc.perform(post("/proposta")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsom))
			.andExpect(status().isCreated());
		
		Proposta proposta = entityManager.createQuery("select p from Proposta p where p.email = :email",Proposta.class)
				.setParameter("email", form.getEmail())
				.getSingleResult();
		
		assertNotNull(proposta);
		assertEquals(Estado.ELEGIVEL, proposta.getEstado());
	}

	@Test
	public void deveCriarUmaPropostaNAO_ELEGIVEL() throws Exception {
		PropostaForm form = new PropostaForm("335.023.480-14", "gabriel@gmail.com", "Gabriel", "vila nova", BigDecimal.TEN);
		String jsom = jsom(form);
		
		Mockito.when(apiDeAnalise.solicitacao(any())).thenThrow(new SolicitacaoComRestricao());
		
		mockMvc.perform(post("/proposta")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsom))
			.andExpect(status().isCreated());
		
		Proposta proposta = entityManager.createQuery("select p from Proposta p where p.email = :email",Proposta.class)
				.setParameter("email", form.getEmail())
				.getSingleResult();
		
		assertNotNull(proposta);
		assertEquals(Estado.NAO_ELEGIVEL, proposta.getEstado());
	}

	private String jsom(PropostaForm form) throws JsonProcessingException {
		String jsom = objectMapper.writeValueAsString(form);
		return jsom;
	}
}
