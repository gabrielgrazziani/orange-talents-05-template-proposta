package br.com.zupacademy.metricas.config.health_check;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;

import io.micrometer.core.instrument.MeterRegistry;

//@Configuration(proxyBeanMethods = false)
public class MyTags {

//    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return (registry) -> registry.config().commonTags("region", "teste");
    }
}
