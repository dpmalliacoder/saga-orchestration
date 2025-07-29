package com.saga.inventoryservice.dto;

import java.util.UUID;

import com.saga.inventoryservice.enums.InventoryStatus;

import lombok.Data;

@Data
public class InventoryResponseDto {
    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private InventoryStatus status;
}
