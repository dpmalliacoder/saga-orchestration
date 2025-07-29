package com.saga.orderorchestration.service.steps;

import com.saga.orderorchestration.dto.enums.WorkflowStepStatus;

import reactor.core.publisher.Mono;

public interface WorkflowStep {
    WorkflowStepStatus getStatus();
    Mono<Boolean> process();
    Mono<Boolean> revert();
}
