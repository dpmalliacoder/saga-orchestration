package com.saga.orderorchestration.service.steps;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.saga.orderorchestration.dto.InventoryRequestDto;
import com.saga.orderorchestration.dto.InventoryResponseDto;
import com.saga.orderorchestration.dto.enums.InventoryStatus;
import com.saga.orderorchestration.dto.enums.WorkflowStepStatus;

import reactor.core.publisher.Mono;

public class InventoryStep implements WorkflowStep {

    private final WebClient webClient;
    private final InventoryRequestDto requestDto;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public InventoryStep(WebClient webClient, InventoryRequestDto requestDto) {
        this.webClient = webClient;
        this.requestDto = requestDto;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/inventory/deduct")
                .body(BodyInserters.fromValue(this.requestDto))
                .retrieve()
                .bodyToMono(InventoryResponseDto.class)
                .map(r -> r.getStatus().equals(InventoryStatus.AVAILABLE))
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                    .post()
                    .uri("/inventory/add")
                    .body(BodyInserters.fromValue(this.requestDto))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .map(r ->true)
                    .onErrorReturn(false);
    }
    
}
