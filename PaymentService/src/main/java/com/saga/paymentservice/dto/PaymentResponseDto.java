package com.saga.paymentservice.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private Integer userId;
    private UUID orderId;
    private Double amount;
    private PaymentStatus status;
}
