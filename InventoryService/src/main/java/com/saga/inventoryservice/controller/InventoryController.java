package com.saga.inventoryservice.controller;

import datadog.trace.api.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.saga.inventoryservice.dto.InventoryRequestDto;
import com.saga.inventoryservice.dto.InventoryResponseDto;
import com.saga.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    Logger log= LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService service;

    @PostMapping("/deduct")
    @Trace
    public InventoryResponseDto deduct(@RequestBody final InventoryRequestDto requestDTO){
        log.info("Inventory deducted");
        return this.service.deductInventory(requestDTO);
    }

    @PostMapping("/add")
    @Trace
    public void add(@RequestBody final InventoryRequestDto requestDTO){
        log.info("Inventory credited");
        this.service.addInventory(requestDTO);
    }

}