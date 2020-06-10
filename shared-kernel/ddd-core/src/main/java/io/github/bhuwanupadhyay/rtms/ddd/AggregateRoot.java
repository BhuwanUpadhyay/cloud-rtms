package io.github.bhuwanupadhyay.rtms.ddd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AggregateRoot<ID extends ValueObject> extends Entity<ID> {

  private final transient List<DomainEvent> domainEvents = new ArrayList<>();

  protected AggregateRoot() {
    super();
  }

  public AggregateRoot(ID id) {
    super(id);
  }

  /**
   * Register domain event
   *
   * @param event domain event
   */
  protected void registerEvent(DomainEvent event) {
    this.domainEvents.add(event);
  }

  /** Clear registered domain events. */
  public void clearDomainEvents() {
    this.domainEvents.clear();
  }

  /** @return list of domain events */
  public List<DomainEvent> getDomainEvents() {
    return Collections.unmodifiableList(domainEvents);
  }
}
