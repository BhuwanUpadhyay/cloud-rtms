package io.github.bhuwanupadhyay.rtms.notification.domain.events;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppCreated extends DomainEvent {

  private final String appId;
  private final String appName;
  private final String status;

  private AppCreated(String appId, String appName, String status) {
    super(DomainEventType.OUTSIDE);
    this.appId = appId;
    this.appName = appName;
    this.status = status;
  }
}
