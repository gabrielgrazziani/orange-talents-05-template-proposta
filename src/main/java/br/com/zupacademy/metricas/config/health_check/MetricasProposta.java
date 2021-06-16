package br.com.zupacademy.metricas.config.health_check;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

@Component
public class MetricasProposta {

	private final MeterRegistry meterRegistry;

    private final Collection<String> strings = new ArrayList<>();

    private final Random random = new Random();

	private Counter contadorDePropostasCriadas;

	private Timer timerConsultarProposta;

    public MetricasProposta(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        criarGauge();
        
        contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags());
        timerConsultarProposta = this.meterRegistry.timer("consultar_proposta", tags());
    }
    
    public void contar() {
    	contadorDePropostasCriadas.increment();
    }
    
    public void medirTempoExecucao(Runnable run) {
    	timerConsultarProposta.record(run);
    }
    
    private Collection<Tag> tags() {
    	Collection<Tag> tags = new ArrayList<>();
    		tags.add(Tag.of("emissora", "Mastercard"));
    		tags.add(Tag.of("banco", "Itaú"));
    	return tags;
    }

    private void criarGauge() {
        Collection<Tag> tags = tags();

        this.meterRegistry.gauge("meu_gauge", tags, strings, Collection::size);
    }
    
    @Scheduled(fixedDelay = 1000)
    private void simulandoGauge() {
        double randomNumber = random.nextInt();
        if (randomNumber % 2 == 0) {
            addString();
        } else {
            removeString();
        }
    }
    
    private void removeString() {
        strings.removeIf(Objects::nonNull);
    }

    private void addString() {
        strings.add(UUID.randomUUID().toString());
    }
}
