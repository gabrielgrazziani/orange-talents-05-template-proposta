package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
class AvisoViagemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	void deveCriarNovoAvisoViagem() throws Exception {
		final String CODIGO_CARTAO = "12345";
		Cartao cartao = new Cartao(CODIGO_CARTAO);
		cartaoRepository.save(cartao);
		
		String jsom = jsom(new AvisoViagemForm("Sâo Paulo",LocalDate.now().plusDays(3)));
		
		mockMvc.perform(put("/cartao/"+CODIGO_CARTAO+"/aviso_viagem")
					.contentType(MediaType.APPLICATION_JSON)
					.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_cartoes:write")))
					.content(jsom))
				.andExpect(status().isOk());

		List<AvisoViagem> avisosViagem = cartaoRepository
				.findByCodigoCartao(CODIGO_CARTAO)
				.get().getAvisosViagem();
		
		assertEquals(1, avisosViagem.size());
	}
	
	private String jsom(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

}
