package com.saga.paymentservice.controller;

import datadog.trace.api.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.saga.paymentservice.dto.PaymentRequestDto;
import com.saga.paymentservice.dto.PaymentResponseDto;
import com.saga.paymentservice.service.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {

    Logger log= LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService service;

    @Trace
    @PostMapping("/debit")
    public PaymentResponseDto debit(@RequestBody PaymentRequestDto requestDTO){
        log.info("Payment Debited");
        return this.service.debit(requestDTO);
    }

    @Trace
    @PostMapping("/credit")
    public void credit(@RequestBody PaymentRequestDto requestDTO){
        log.info("Payment credited");
        this.service.credit(requestDTO);
    }

}
