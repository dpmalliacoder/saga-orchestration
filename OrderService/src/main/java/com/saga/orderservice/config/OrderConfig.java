package com.saga.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saga.orderservice.dto.OrchestratorRequestDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class OrderConfig {
    Logger log = LoggerFactory.getLogger(OrderConfig.class);

    @Bean
    public Sinks.Many<OrchestratorRequestDto> orderSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<OrchestratorRequestDto>> orderSupplier(Sinks.Many<OrchestratorRequestDto> sink){
         return () -> sink.asFlux().doOnNext(e -> log.info("Sending to Kafka: {}", e));
    }

}