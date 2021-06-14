package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BloqueioCataoControllerTest {
	
	@Autowired
	CartaoRepository cartaoRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void bloqueiaCartao() throws Exception {
		Cartao cartao = new Cartao("12345");
		cartaoRepository.save(cartao);
		assertFalse(cartao.estaBloqueado());

		String jsom = jsom(cartao);
		
		mockMvc.perform(put("/cartao/12345/bloquear")
				.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_cartoes:write")))
				.header("User-Agent", "mock-mvc")
				.content(jsom))
			.andExpect(status().isOk());
		
		cartao = cartaoRepository.findByCodigoCartao("12345").get();
		
		assertTrue(cartao.estaBloqueado());
		List<Bloqueio> bloqueios = cartao.getBloqueios();
		assertEquals(1, bloqueios.size());
	}
	
	@Test
	void tentarBloqueiaCartaoJaBloqueado() throws Exception {
		Cartao cartao = cartaoBloqueado("12345");
		cartaoRepository.save(cartao);
		assertTrue(cartao.estaBloqueado());

		String jsom = jsom(cartao);
		
		mockMvc.perform(put("/cartao/12345/bloquear")
				.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_cartoes:write")))
				.content(jsom))
			.andExpect(status().isUnprocessableEntity());
		
		cartao = cartaoRepository.findByCodigoCartao("12345").get();
		
		assertTrue(cartao.estaBloqueado());
		List<Bloqueio> bloqueios = cartao.getBloqueios();
		assertEquals(1, bloqueios.size());
	}

	private String jsom(Cartao cartao) throws JsonProcessingException {
		return objectMapper.writeValueAsString(cartao);
	}

	private Cartao cartaoBloqueado(String id) {
		Cartao cartao =new Cartao("12345");
		cartao.tentarBloquear(new Bloqueio("3534231234","127.0.0.1", cartao));
		return cartao;
	}

}
