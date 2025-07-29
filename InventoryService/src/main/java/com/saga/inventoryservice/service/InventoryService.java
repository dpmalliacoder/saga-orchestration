package com.saga.inventoryservice.service;

import org.springframework.stereotype.Service;

import com.saga.inventoryservice.dto.InventoryRequestDto;
import com.saga.inventoryservice.dto.InventoryResponseDto;
import com.saga.inventoryservice.entity.OrderInventoryConsumption;
import com.saga.inventoryservice.enums.InventoryStatus;
import com.saga.inventoryservice.repository.OrderInventoryConsumptionRepository;
import com.saga.inventoryservice.repository.OrderInventoryRepository;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {

    private final OrderInventoryRepository orderInventoryRepository;
    private final OrderInventoryConsumptionRepository orderInventoryConsumptionRepository;

    public InventoryService(OrderInventoryRepository orderInventoryRepository,
        OrderInventoryConsumptionRepository orderInventoryConsumptionRepository) {
        this.orderInventoryRepository = orderInventoryRepository;
        this.orderInventoryConsumptionRepository = orderInventoryConsumptionRepository;
    }

    @Transactional
    public InventoryResponseDto deductInventory(final InventoryRequestDto requestDTO){
        InventoryResponseDto responseDTO = new InventoryResponseDto();
        responseDTO.setOrderId(requestDTO.getOrderId());
        responseDTO.setUserId(requestDTO.getUserId());
        responseDTO.setProductId(requestDTO.getProductId());
        responseDTO.setStatus(InventoryStatus.UNAVAILABLE);
        return orderInventoryRepository.findById(requestDTO.getProductId())
            .filter(i -> i.getAvailableInventory() > 0 )
            .map(orderInventory -> {
                            orderInventory.setAvailableInventory(
                                orderInventory.getAvailableInventory() - 1);
                            responseDTO.setStatus(InventoryStatus.AVAILABLE);
                            orderInventoryConsumptionRepository.findById(requestDTO.getOrderId())
                            .ifPresentOrElse(orderInventoryConsumption -> {
                                orderInventoryConsumption.setQuantityConsumed(
                                    orderInventoryConsumption.getQuantityConsumed() + 1);
                                orderInventoryConsumptionRepository.save(orderInventoryConsumption);
                            }, () -> {
                                orderInventoryConsumptionRepository.save(
                                    OrderInventoryConsumption.of(requestDTO.getOrderId(), requestDTO.getProductId(), 1));
                            });

                            return responseDTO;
                        })
                .orElse(responseDTO);
    }

    @Transactional
    public void addInventory(final InventoryRequestDto requestDTO){

         orderInventoryConsumptionRepository.findById(requestDTO.getOrderId())
         .ifPresent(orderInventoryConsumption -> {
            orderInventoryRepository.findById(orderInventoryConsumption.getProductId())
                .ifPresent(orderInventory -> {
                    orderInventory.setAvailableInventory(
                        orderInventory.getAvailableInventory() + orderInventoryConsumption.getQuantityConsumed());
                });
             orderInventoryConsumptionRepository.delete(orderInventoryConsumption);
         });
    }

}