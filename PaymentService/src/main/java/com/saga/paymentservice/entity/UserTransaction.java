package com.saga.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserTransaction {

    @Id
    private UUID orderId;
    private int userId;
    private Double amount;

}
