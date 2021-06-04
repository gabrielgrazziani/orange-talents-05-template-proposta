package br.com.zupacademy.metricas.config.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zupacademy.metricas.geral.SolicitacaoResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

public class ApiDeAnaliseErroDecoder implements ErrorDecoder {

	private final Default defaulter = new Default();
	private ObjectMapper objectMapper;

	public ApiDeAnaliseErroDecoder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Exception decode(String methodKey, Response response) {

		if (response.status() == 422 && resultadoSolicitacao(response).equals("COM_RESTRICAO")) {

			return new SolicitacaoComRestricao();
		}

		return defaulter.decode(methodKey, response);
	}

	private String resultadoSolicitacao(Response response) {
		String body = response.body().toString();
		SolicitacaoResponse solicitacao;
		try {
			solicitacao = objectMapper.readValue(body, SolicitacaoResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		String resultadoSolicitacao = solicitacao.getResultadoSolicitacao();
		return resultadoSolicitacao;
	}

}
