package br.com.zupacademy.metricas.config.health_check;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class MeuHealthCheck implements HealthIndicator{

	@Override
    public Health health() {
        Map<String, String> details = new HashMap<>();
        details.put("versão", "1.2.3");
        details.put("descrição", "Meu primeiro Health Check customizado!");
        
        return Health.status(Status.UP).withDetails(details).build();
    }
}
