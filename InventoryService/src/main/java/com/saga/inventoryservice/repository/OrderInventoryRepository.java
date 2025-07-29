package com.saga.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saga.inventoryservice.entity.OrderInventory;

@Repository
public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Integer> {
    
}
