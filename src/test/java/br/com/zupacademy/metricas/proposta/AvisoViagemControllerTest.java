package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

class AvisoViagemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CartaoRepository cartaoRepository;

	@Test
	void deveCriarNovoAvisoViagem() throws Exception {
		final String CODIGO_CARTAO = "12345";
		Cartao cartao = new Cartao(CODIGO_CARTAO);
		cartaoRepository.save(cartao);
		
		final String FINGERPRINT = "dGVzdGU=";
		String jsom = "{\"fingerprint\":\""+FINGERPRINT+"\"}";
		
		mockMvc.perform(post("/cartao/"+CODIGO_CARTAO+"/biometria")
					.contentType(MediaType.APPLICATION_JSON)
					.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_cartoes:write")))
					.content(jsom))
				.andExpect(status().isCreated());

		List<Biometria> biomatrias =cartaoRepository
				.findByCodigoCartao(CODIGO_CARTAO)
				.get().getBiomatrias();
		
		assertEquals(1, biomatrias.size());
		assertEquals(FINGERPRINT, biomatrias.get(0).getFingerprint());
	}
	
//	private String jsom(Cartao cartao) throws JsonProcessingException {
//		return objectMapper.writeValueAsString(cartao);
//	}

}
