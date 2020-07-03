package io.github.bhuwanupadhyay.rtms.inventory.application.queryservices;

import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.WorkflowInfo;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkflowQueryService {

  private final ExternalWorkflowEngineClient workflowEngineClient;

  public List<ActionInfo> getActions(WorkflowInfo workflowInfo) {
    List<TaskResponse> tasks = workflowEngineClient.getTasks(workflowInfo.getProcessId());
    return Optional.ofNullable(tasks)
        .orElseGet(ArrayList::new)
        .stream()
        .map(ActionInfo::create)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }


}
