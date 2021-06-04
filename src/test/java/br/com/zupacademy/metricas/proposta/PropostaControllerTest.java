package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc()
@AutoConfigureDataJpa
@Transactional
public class PropostaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void deveCriarUmaPropostaELEGIVEL() throws Exception {
		PropostaForm form = new PropostaForm("768.250.480-31", "gabriel@gmail.com", "Gabriel", "vila nova", BigDecimal.TEN);
		String jsom = jsom(form);
		
		assertTrue(jsom.contains("768.250.480-31"));
		
		mockMvc.perform(post("/proposta")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsom))
			.andExpect(status().isCreated());
	}

	@Test
	public void deveCriarUmaPropostaNAO_ELEGIVEL() throws Exception {
		PropostaForm form = new PropostaForm("335.023.480-14", "gabriel@gmail.com", "Gabriel", "vila nova", BigDecimal.TEN);
		String jsom = jsom(form);
		
		mockMvc.perform(post("/proposta")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsom))
			.andExpect(status().isCreated());
	}

	private String jsom(PropostaForm form) throws JsonProcessingException {
		String jsom = objectMapper.writeValueAsString(form);
		return jsom;
	}
}
