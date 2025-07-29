package com.saga.orderorchestration.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentRequestDto {
    private Integer userId;
    private UUID orderId;
    private Double amount;
}
