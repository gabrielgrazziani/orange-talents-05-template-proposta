package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class BiometriaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CartaoRepository cartaoRepository;

	@Test
	void deveCriarNovoBiometria() throws Exception {
		final String CODIGO_CARTAO = "12345";
		Cartao cartao = new Cartao(CODIGO_CARTAO);
		cartaoRepository.save(cartao);
		
		final String FINGERPRINT = "dGVzdGU=";
		String jsom = "{\"fingerprint\":\""+FINGERPRINT+"\"}";
		
		mockMvc.perform(post("/cartao/"+CODIGO_CARTAO+"/biometria")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsom))
				.andExpect(status().isCreated());

		List<Biometria> biomatrias =cartaoRepository
				.findByCodigoCartao(CODIGO_CARTAO)
				.get().getBiomatrias();
		
		assertEquals(1, biomatrias.size());
		assertEquals(FINGERPRINT, biomatrias.get(0).getFingerprint());
	}

}
