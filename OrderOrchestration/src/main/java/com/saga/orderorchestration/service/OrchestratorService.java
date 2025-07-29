package com.saga.orderorchestration.service;

import datadog.trace.api.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.saga.orderorchestration.dto.*;
import com.saga.orderorchestration.dto.enums.*;
import com.saga.orderorchestration.service.steps.*;
import com.saga.orderorchestration.exceptions.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class OrchestratorService {
    Logger log= LoggerFactory.getLogger(OrchestratorService.class);
    @Autowired
    @Qualifier("payment")
    private WebClient paymentClient;

    @Autowired
    @Qualifier("inventory")
    private WebClient inventoryClient;

    @Trace
    public Mono<OrchestratorResponseDto> orderProduct(final OrchestratorRequestDto requestDto){
        Workflow orderWorkflow = this.getOrderWorkflow(requestDto);
        return Flux.fromStream(() -> orderWorkflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .handle(((aBoolean, synchronousSink) -> {
                    if(aBoolean) {
                        log.info("Order created successfully");
                        synchronousSink.next(true);
                    }
                    else {
                        log.info("Create Order failed");
                        synchronousSink.error(new WorkflowException("create order failed!"));
                    }
                }))
                .then(Mono.fromCallable(() -> getResponseDto(requestDto, OrderStatus.ORDER_COMPLETED)))
                .onErrorResume(ex -> this.revertOrder(orderWorkflow, requestDto));

    }

    private Mono<OrchestratorResponseDto> revertOrder(final Workflow workflow, final OrchestratorRequestDto requestDto){
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.just(this.getResponseDto(requestDto, OrderStatus.ORDER_CANCELLED)));
    }

    private Workflow getOrderWorkflow(OrchestratorRequestDto requestDto){
        WorkflowStep paymentStep = new PaymentStep(this.paymentClient, this.getPaymentRequestDto(requestDto));
        WorkflowStep inventoryStep = new InventoryStep(this.inventoryClient, this.getInventoryRequestDto(requestDto));
        return new OrderWorkflow(List.of(paymentStep, inventoryStep));
    }

    private OrchestratorResponseDto getResponseDto(OrchestratorRequestDto requestDto, OrderStatus status){
        OrchestratorResponseDto responseDto = new OrchestratorResponseDto();
        responseDto.setOrderId(requestDto.getOrderId());
        responseDto.setAmount(requestDto.getAmount());
        responseDto.setProductId(requestDto.getProductId());
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setStatus(status);
        return responseDto;
    }

    private PaymentRequestDto getPaymentRequestDto(OrchestratorRequestDto requestDto){
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setUserId(requestDto.getUserId());
        paymentRequestDto.setAmount(requestDto.getAmount());
        paymentRequestDto.setOrderId(requestDto.getOrderId());
        return paymentRequestDto;
    }

    private InventoryRequestDto getInventoryRequestDto(OrchestratorRequestDto requestDto){
        InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
        inventoryRequestDto.setUserId(requestDto.getUserId());
        inventoryRequestDto.setProductId(requestDto.getProductId());
        inventoryRequestDto.setOrderId(requestDto.getOrderId());
        return inventoryRequestDto;
    }
}
