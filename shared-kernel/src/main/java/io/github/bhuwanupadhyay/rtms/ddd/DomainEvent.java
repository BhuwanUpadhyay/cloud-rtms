package io.github.bhuwanupadhyay.rtms.ddd;

import java.util.Objects;
import java.util.UUID;

public abstract class DomainEvent {

  private final String eventId = UUID.randomUUID().toString();

  private final String eventClassName = getClass().getName();

  public String getEventId() {
    return eventId;
  }

  public String getEventClassName() {
    return eventClassName;
  }
}
