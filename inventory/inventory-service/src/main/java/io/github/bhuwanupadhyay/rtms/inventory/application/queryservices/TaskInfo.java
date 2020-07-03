package io.github.bhuwanupadhyay.rtms.inventory.application.queryservices;

import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient.TaskResponse;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskInfo {
  private final String id;
  private final String name;

  public TaskInfo(TaskResponse response) {
    this.id = response.getId();
    this.name = response.getName();
  }
}
