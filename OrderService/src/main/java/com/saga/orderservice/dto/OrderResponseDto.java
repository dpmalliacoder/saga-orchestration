package com.saga.orderservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderResponseDto {
    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private Double amount;
    private OrderStatus status;
}
