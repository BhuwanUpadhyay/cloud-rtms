package io.github.bhuwanupadhyay.rtms.ddd;

import java.util.Optional;

public abstract class DomainRepository<T extends AggregateRoot<ID>, ID extends ValueObject> {

  private final DomainEventPublisher publisher;

  protected DomainRepository(DomainEventPublisher publisher) {
    this.publisher = publisher;
  }

  public abstract Optional<T> findOne(ID id);

  public T find(ID id) {
    return this.findOne(id)
        .orElseThrow(() -> new DomainEntityNotFound(this.getClass().getName(), id));
  }

  public ID save(T entity) {
    DomainAsserts.begin(entity).notNull(DomainError.create(this, "EntityIsRequired")).end();
    this.persist(entity);
    entity.getDomainEvents().forEach(publisher::publish);
    entity.clearDomainEvents();
    return entity.getId();
  }

  protected abstract void persist(T entity);

  public abstract ID nextId();
}
