package br.com.zupacademy.metricas.proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zupacademy.metricas.aviso_viagem.AvisoViagem;
import br.com.zupacademy.metricas.aviso_viagem.AvisoViagemForm;
import br.com.zupacademy.metricas.cartao.Cartao;
import br.com.zupacademy.metricas.cartao.CartaoRepository;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.ResultadoAvisoViagem;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.ResultadoAvisoViagemEnum;
import br.com.zupacademy.metricas.geral.api.ApiDeCartao.SolicitacaoAvisoViagem;
import feign.FeignException;
import feign.Request;
import feign.Response;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles("test")
class AvisoViagemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@MockBean
	private ApiDeCartao apiDeCartao;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void deveCriarNovoAvisoViagem() throws Exception {
		final String CODIGO_CARTAO = "12345";
		Cartao cartao = new Cartao(CODIGO_CARTAO);
		cartaoRepository.save(cartao);
		
		AvisoViagemForm form = new AvisoViagemForm("São Paulo",LocalDate.now().plusDays(3));
		when(apiDeCartao.avisoViagem(eq(CODIGO_CARTAO),any(SolicitacaoAvisoViagem.class)))
			.thenReturn(new ResultadoAvisoViagem(ResultadoAvisoViagemEnum.CRIADO));
		
		
		mockMvc.perform(put("/cartao/"+CODIGO_CARTAO+"/aviso_viagem")
					.contentType(MediaType.APPLICATION_JSON)
					.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_cartoes:write")))
					.content(jsom(form)))
				.andExpect(status().isOk());

		List<AvisoViagem> avisosViagem = cartaoRepository
				.findByCodigoCartao(CODIGO_CARTAO)
				.get().getAvisosViagem();
		
		Mockito.verify(apiDeCartao,times(1))
			.avisoViagem(eq(CODIGO_CARTAO), any(SolicitacaoAvisoViagem.class));
		assertEquals(1, avisosViagem.size());
	}
	
	@Test
	void naoDeveCriarNovoAvisoViagem() throws Exception {
		final String CODIGO_CARTAO = "12345";
		Cartao cartao = new Cartao(CODIGO_CARTAO);
		cartaoRepository.save(cartao);
		
		AvisoViagemForm form = new AvisoViagemForm("São Paulo",LocalDate.now().plusDays(3));
		when(apiDeCartao.avisoViagem(eq(CODIGO_CARTAO),any(SolicitacaoAvisoViagem.class)))
			.thenThrow(erroFeign(400,"bad-request"));
		
		
		mockMvc.perform(put("/cartao/"+CODIGO_CARTAO+"/aviso_viagem")
					.contentType(MediaType.APPLICATION_JSON)
					.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_cartoes:write")))
					.content(jsom(form)))
				.andExpect(status().isBadRequest());

		List<AvisoViagem> avisosViagem = cartaoRepository
				.findByCodigoCartao(CODIGO_CARTAO)
				.get().getAvisosViagem();
		

		Mockito.verify(apiDeCartao,times(1))
			.avisoViagem(eq(CODIGO_CARTAO), any(SolicitacaoAvisoViagem.class));
		assertEquals(0, avisosViagem.size());
	}

	private FeignException erroFeign(int status, String reason) {
		return FeignException.errorStatus("bloqueiaCartao", 
				Response.builder()
						.status(status)
						.headers(Map.of())
						.reason(reason)
						.request(Request.create(
                                Request.HttpMethod.POST,
                                "",
                                Map.of(),
                                null,
                                null,
                                null))
						.build());
	}
	
	private String jsom(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

}
