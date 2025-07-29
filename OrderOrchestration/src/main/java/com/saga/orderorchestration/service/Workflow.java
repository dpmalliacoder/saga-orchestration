package com.saga.orderorchestration.service;

import com.saga.orderorchestration.service.steps.WorkflowStep;
import java.util.List;

public interface Workflow {
    List<WorkflowStep> getSteps();
}
