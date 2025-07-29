package com.saga.orderservice.controller;

import datadog.trace.api.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.saga.orderservice.service.OrderService;
import com.saga.orderservice.dto.OrderRequestDto;
import com.saga.orderservice.dto.OrderResponseDto;
import com.saga.orderservice.entity.PurchaseOrder;
import java.util.UUID;

@RestController
@RequestMapping("order")
public class OrderController {
    Logger log= LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService service;

    @Trace
    @PostMapping("/create")
    public Mono<PurchaseOrder> createOrder(@RequestBody OrderRequestDto requestDTO){
        log.info("Create order :{}", requestDTO);
        requestDTO.setOrderId(UUID.randomUUID());
        return this.service.createOrder(requestDTO);
    }

    @Trace
    @GetMapping("/all")
    public Flux<OrderResponseDto> getOrders(){
        log.info("Fetching all orders");
        return this.service.getAll();
    }
}
