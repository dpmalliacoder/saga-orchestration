package com.saga.orderservice.entity;

import com.saga.orderservice.dto.OrderStatus;
import lombok.Data;
import lombok.ToString;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
@ToString
public class PurchaseOrder {
    @Id
    private UUID id;
    private Integer userId;
    private Integer productId;
    private Double price;
    private OrderStatus status;
    
}
