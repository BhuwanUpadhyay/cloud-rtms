package io.github.bhuwanupadhyay.rtms.inventory.application.outboundservices;

import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.brokers.stream.AppEventSource;
import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import io.github.bhuwanupadhyay.rtms.ddd.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(AppEventSource.class)
@RequiredArgsConstructor
public class AppEventPublisherService implements DomainEventPublisher {
  private final AppEventSource appEventSource;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publish(DomainEvent domainEvent) {
    log.info("Handling event [{}].", domainEvent.getEventClassName());
    if (domainEvent.isInsideContext()) {
      applicationEventPublisher.publishEvent(domainEvent);
    }
    if (domainEvent.isOutsideContext()) {
      appEventSource.rtmsOutput().send(MessageBuilder.withPayload(domainEvent).build());
    }
    log.info("Successfully published the event [{}].", domainEvent.getEventClassName());
  }
}
