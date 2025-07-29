package com.saga.orderservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saga.orderservice.dto.OrchestratorResponseDto;
import com.saga.orderservice.respository.PurchaseOrderRepository;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
public class OrderEventUpdateService {

    Logger log = LoggerFactory.getLogger(OrderEventUpdateService.class);
    @Autowired
    private PurchaseOrderRepository repository;

    @Transactional
    public Mono<Void> updateOrder(final OrchestratorResponseDto responseDTO) {
        return Mono.justOrEmpty(this.repository.findById(responseDTO.getOrderId())
                .map(purchaseOrder -> {
                    log.info("Updating order ID: {} with status {}", responseDTO.getOrderId(), responseDTO.getStatus());
                    purchaseOrder.setStatus(responseDTO.getStatus());
                    this.repository.save(purchaseOrder);
                    return purchaseOrder;
                }))
                .then();
    }

    
}
