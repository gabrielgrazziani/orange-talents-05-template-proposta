package br.com.zupacademy.metricas.config.feign;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiDeAnaliseConfig {
	
	@Bean
    public ApiDeAnaliseErroDecoder clientErrorDecoder(ObjectMapper objectMapper) {
        return new ApiDeAnaliseErroDecoder(objectMapper);
    }

}
