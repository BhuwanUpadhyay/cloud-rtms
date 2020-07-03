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
public class WorkflowTaskQueryService {

  private final ExternalWorkflowEngineClient workflowEngineClient;

  public List<TaskInfo> getTaskInfos(WorkflowInfo workflowInfo) {
    List<TaskResponse> tasks = workflowEngineClient.getTasks(workflowInfo.getProcessId());
    return Optional.ofNullable(tasks)
        .orElseGet(ArrayList::new)
        .stream()
        .map(TaskInfo::new)
        .collect(Collectors.toList());
  }


}
