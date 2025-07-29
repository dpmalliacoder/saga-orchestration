package com.saga.orderorchestration.exceptions;

public class WorkflowException extends RuntimeException {

    public WorkflowException(String message) {
        super(message);
    }
    
}
