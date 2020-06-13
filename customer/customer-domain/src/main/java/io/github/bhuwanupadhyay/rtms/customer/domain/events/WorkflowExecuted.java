package io.github.bhuwanupadhyay.rtms.customer.domain.events;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkflowExecuted extends DomainEvent {
  private final String action;
  private final String status;

  public WorkflowExecuted(String action, String status) {
    super(DomainEventType.OUTSIDE);
    this.action = action;
    this.status = status;
  }
}
