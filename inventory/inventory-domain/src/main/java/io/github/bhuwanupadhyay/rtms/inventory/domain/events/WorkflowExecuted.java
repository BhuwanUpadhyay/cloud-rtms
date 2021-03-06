package io.github.bhuwanupadhyay.rtms.inventory.domain.events;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkflowExecuted extends DomainEvent {
  private final String action;
  private final String status;

  public WorkflowExecuted(String action, String status) {
    this.action = action;
    this.status = status;
  }
}
