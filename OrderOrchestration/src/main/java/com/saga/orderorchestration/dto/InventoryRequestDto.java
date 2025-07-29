package com.saga.orderorchestration.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class InventoryRequestDto {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
}
