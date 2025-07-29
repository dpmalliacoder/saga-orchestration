package com.saga.orderservice.dto;

import lombok.Data;

import java.util.UUID; 

@Data
public class OrchestratorResponseDto {
    
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Double amount;
    private OrderStatus status;
}
