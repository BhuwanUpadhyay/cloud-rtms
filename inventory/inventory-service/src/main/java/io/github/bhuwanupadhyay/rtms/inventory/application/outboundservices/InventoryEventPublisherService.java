package io.github.bhuwanupadhyay.rtms.inventory.application.outboundservices;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import io.github.bhuwanupadhyay.rtms.ddd.DomainEventPublisher;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.brokers.stream.InventoryEventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(InventoryEventSource.class)
@RequiredArgsConstructor
public class InventoryEventPublisherService implements DomainEventPublisher {
  private final InventoryEventSource inventoryEventSource;

  @Override
  public void publish(DomainEvent domainEvent) {
    log.info("Handling event [{}].", domainEvent.getEventClassName());
    inventoryEventSource.rtmsOutput().send(MessageBuilder.withPayload(domainEvent).build());
    log.info("Successfully published the event [{}].", domainEvent.getEventClassName());
  }
}
