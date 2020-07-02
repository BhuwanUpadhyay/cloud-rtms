package io.github.bhuwanupadhyay.rtms.inventory.domain.events;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.WorkflowInfo;
import lombok.Getter;

@Getter
public class WorkflowAssigned extends DomainEvent {
  private final String processId;
  private final String currentTask;
  private final String currentProcess;

  public WorkflowAssigned(WorkflowInfo info) {
    this.processId = info.getProcessId();
    this.currentTask = info.getCurrentTask();
    this.currentProcess = info.getCurrentProcess();
  }


}
