package io.github.bhuwanupadhyay.rtms.inventory.interfaces.events;

import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.brokers.stream.InventoryEventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableBinding(InventoryEventSource.class)
public class InventoryEventHandler {

  @StreamListener(target = InventoryEventSource.INPUT)
  public void receiveEvent(String paymentReceived) {
    log.info("Receive event [PaymentReceivedV1].");
    log.debug("Event payload {}.", paymentReceived);
    log.info("Successfully processed event [PaymentReceivedV1].");
  }
}
