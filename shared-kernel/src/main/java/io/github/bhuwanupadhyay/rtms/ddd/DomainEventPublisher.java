package io.github.bhuwanupadhyay.rtms.ddd;

@FunctionalInterface
public interface DomainEventPublisher {

  void publish(DomainEvent domainEvent);
}
