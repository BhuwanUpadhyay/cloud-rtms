package io.github.bhuwanupadhyay.rtms.inventory.application.outboundservices.acl;

import io.github.bhuwanupadhyay.rtms.command.RegisterWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalWorkflowEngineService {

  private final ExternalWorkflowEngineClient workflowEngineClient;

  public RegisterWorkflowCommand startWorkflow() {
    String processId = workflowEngineClient.startWorkflow().getProcessInstanceId();
    return RegisterWorkflowCommand.builder().processId(processId).build();
  }

  public String submitTask() {
    return workflowEngineClient.startWorkflow().getProcessInstanceId();
  }

}
