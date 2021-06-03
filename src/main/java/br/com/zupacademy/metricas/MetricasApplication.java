package br.com.zupacademy.metricas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MetricasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricasApplication.class, args);
	}

}
