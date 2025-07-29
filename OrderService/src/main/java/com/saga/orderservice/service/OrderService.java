package com.saga.orderservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saga.orderservice.config.OrderConfig;
import com.saga.orderservice.dto.OrchestratorRequestDto;
import com.saga.orderservice.dto.OrderRequestDto;
import com.saga.orderservice.dto.OrderResponseDto;
import com.saga.orderservice.dto.OrderStatus;
import com.saga.orderservice.entity.PurchaseOrder;
import com.saga.orderservice.respository.PurchaseOrderRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import java.util.Map;

@Service
public class OrderService {

    Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private Sinks.Many<OrchestratorRequestDto> sink;

    private static final Map<Integer, Double> PRODUCT_PRICE =  Map.of(
            1, 100d,
            2, 200d,
            3, 300d
    );

    public Mono<PurchaseOrder> createOrder(OrderRequestDto orderRequestDTO){
        // return this.purchaseOrderRepository.save(this.dtoToEntity(orderRequestDTO))
        //         .doOnNext(e -> {
        //             orderRequestDTO.setOrderId(e.getId());
        //             this.emitEvent(orderRequestDTO);
        //         });
        return Mono.fromCallable(() -> this.purchaseOrderRepository.save(this.dtoToEntity(orderRequestDTO)))
                .doOnNext(e -> {
                   log.info("Request Id {}", e.getId()); 
                    orderRequestDTO.setOrderId(e.getId());
                    this.emitEvent(orderRequestDTO);
                });
                
    }

    public Flux<OrderResponseDto> getAll() {
        // return this.purchaseOrderRepository.findAll()
                // .map(this::entityToDto);

        return Flux.fromIterable(this.purchaseOrderRepository.findAll())
                .map(this::entityToDto);
    }

    private void emitEvent(OrderRequestDto orderRequestDTO){
        this.sink.tryEmitNext(this.getOrchestratorRequestDTO(orderRequestDTO));
    }

    private PurchaseOrder dtoToEntity(final OrderRequestDto dto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(dto.getOrderId());
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(PRODUCT_PRICE.get(purchaseOrder.getProductId()));
        return purchaseOrder;
    }

    private OrderResponseDto entityToDto(final PurchaseOrder purchaseOrder){
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(purchaseOrder.getId());
        dto.setProductId(purchaseOrder.getProductId());
        dto.setUserId(purchaseOrder.getUserId());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setAmount(purchaseOrder.getPrice());
        return dto;
    }

    public OrchestratorRequestDto getOrchestratorRequestDTO(OrderRequestDto orderRequestDTO){
        OrchestratorRequestDto requestDTO = new OrchestratorRequestDto();
        requestDTO.setUserId(orderRequestDTO.getUserId());
        requestDTO.setAmount(PRODUCT_PRICE.get(orderRequestDTO.getProductId()));
        requestDTO.setOrderId(orderRequestDTO.getOrderId());
        requestDTO.setProductId(orderRequestDTO.getProductId());
        return requestDTO;
    }
}
