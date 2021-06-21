package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zupacademy.metricas.bloqueio.Bloqueio;
import br.com.zupacademy.metricas.cartao.Cartao;
import br.com.zupacademy.metricas.cartao.CartaoRepository;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.ResultadoBloqueio;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.ResultadoBloqueioEnum;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.SolicitacaoBloqueio;
import feign.FeignException;
import feign.Request;
import feign.Response;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class BloqueioCataoControllerTest {
	
	@Autowired
	TransactionTemplate transaction;
	
	@Autowired
	CartaoRepository cartaoRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	ApiDeCartao apiDeCartao;

	@Test
	void bloqueiaCartao() throws Exception {
		Cartao cartao = new Cartao("12345");
		cartaoRepository.save(cartao);
		assertFalse(cartao.estaBloqueado());

		String jsom = jsom(cartao);
		
		when(apiDeCartao.bloqueiaCartao(eq("12345"),any(SolicitacaoBloqueio.class)))
			.thenReturn(new ResultadoBloqueio(ResultadoBloqueioEnum.BLOQUEADO));
		
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
				.header("User-Agent", "mock-mvc")
				.content(jsom))
			.andExpect(status().isUnprocessableEntity());
		
		cartao = cartaoRepository.findByCodigoCartao("12345").get();
		
		assertTrue(cartao.estaBloqueado());
		List<Bloqueio> bloqueios = cartao.getBloqueios();
		assertEquals(1, bloqueios.size());
	}
	
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void apiDeCartaoNaoAceitaBloqueio() throws Exception {
		Cartao cartao = new Cartao("12345");
		cartaoRepository.save(cartao);
		assertFalse(cartao.estaBloqueado());

		String jsom = jsom(cartao);
		
		when(apiDeCartao.bloqueiaCartao(eq("12345"),any(SolicitacaoBloqueio.class)))
			.thenThrow(FeignException.errorStatus("bloqueiaCartao", 
							Response.builder()
									.status(400)
									.headers(Map.of())
									.reason("Not found")
									.request(Request.create(
	                                        Request.HttpMethod.POST,
	                                        "",
	                                        Map.of(),
	                                        null,
	                                        null,
	                                        null))
									.build()));
		
		mockMvc.perform(put("/cartao/12345/bloquear")
				.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_cartoes:write")))
				.header("User-Agent", "mock-mvc")
				.content(jsom))
			.andExpect(status().isBadRequest());
		
		
		transaction.execute(status -> {
			Cartao cartaoBaonco = cartaoRepository.findByCodigoCartao("12345").get();
			
			assertFalse(cartaoBaonco.estaBloqueado());
			List<Bloqueio> bloqueios = cartaoBaonco.getBloqueios();
			assertEquals(0, bloqueios.size());
			return cartaoBaonco;
		});
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
