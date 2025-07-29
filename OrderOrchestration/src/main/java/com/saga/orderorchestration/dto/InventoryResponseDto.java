package com.saga.orderorchestration.dto;

import java.util.UUID;

import com.saga.orderorchestration.dto.enums.InventoryStatus;

import lombok.Data;

@Data
public class InventoryResponseDto {
    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private InventoryStatus status;
}