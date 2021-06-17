package br.com.zupacademy.metricas.config.health_check;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import br.com.zupacademy.metricas.proposta.Proposta;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Component
public class MetricasProposta {

	private final MeterRegistry meterRegistry;

    public MetricasProposta(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    
    public void contarNovaProposta(String... tags) {
    	contar("proposta_criada",tags);
    }
    
    public Proposta calcularTempo(Supplier<Proposta> f,String... tags) {
    	Timer timer = this.meterRegistry.timer("proposta_consultar",tags);
    	return timer.record(f);
    }

	public void contar(String nome,String... tags) {
		Counter contadorDePropostasCriadas = this.meterRegistry.counter(nome,tags);
    	contadorDePropostasCriadas.increment();
	}
   
}
