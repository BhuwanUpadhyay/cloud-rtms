package io.github.bhuwanupadhyay.rtms.ddd;

import lombok.NonNull;

import java.util.Optional;

public abstract class DomainRepository<T extends AggregateRoot<ID>, ID extends ValueObject> {

  private final DomainEventPublisher publisher;

  protected DomainRepository(@NonNull DomainEventPublisher publisher) {
    this.publisher = publisher;
  }

  public abstract Optional<T> findOne(@NonNull ID id);

  public T find(@NonNull ID id) {
    return this.findOne(id)
        .orElseThrow(() -> new DomainEntityNotFound(this.getClass().getName(), id));
  }

  public ID save(@NonNull T entity) {
    this.persist(entity);
    entity.getDomainEvents().forEach(publisher::publish);
    entity.clearDomainEvents();
    return entity.getId();
  }

  protected abstract void persist(T entity);

  public abstract ID nextId();
}
