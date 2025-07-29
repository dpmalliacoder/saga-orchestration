package com.saga.orderservice.config;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saga.orderservice.dto.OrchestratorRequestDto;
import com.saga.orderservice.dto.OrchestratorResponseDto;
import com.saga.orderservice.service.OrderEventUpdateService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class OrderEventHandler {

    Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    // @Autowired
    // private Flux<OrchestratorRequestDto> flux;

    @Autowired
    private OrderEventUpdateService service;

    @Bean
    public Supplier<Flux<OrchestratorRequestDto>> supplier(Sinks.Many<OrchestratorRequestDto> sink){
        return () -> sink.asFlux().doOnNext(e -> log.info("Sending to Kafka: {}", e));
    }

    @Bean
    public Consumer<OrchestratorResponseDto> consumer(){
        // return f -> f
        //         .doOnNext(c -> log.info("Consuming :: " + c))
        //         .flatMap(responseDTO -> this.service.updateOrder(responseDTO));
        return pe -> {
            this.service.updateOrder(pe);
            log.info("Order updated successfully: {}", pe);
        };
    }
    
}
