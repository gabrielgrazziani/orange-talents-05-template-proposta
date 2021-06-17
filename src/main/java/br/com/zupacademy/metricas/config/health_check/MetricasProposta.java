package br.com.zupacademy.metricas.config.health_check;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class MetricasProposta {

	private final MeterRegistry meterRegistry;

    public MetricasProposta(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    
    public void contar() {
    	Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada");
    	contadorDePropostasCriadas.increment();
    }
   
}
