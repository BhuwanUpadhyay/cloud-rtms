package io.github.bhuwanupadhyay.rtms.customer.interfaces.events;

import io.github.bhuwanupadhyay.rtms.customer.infrastructure.brokers.stream.AppEventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableBinding(AppEventSource.class)
public class AppEventHandler {

  @StreamListener(target = AppEventSource.INPUT)
  public void receiveEvent(String paymentReceived) {
    log.info("Receive event [PaymentReceivedV1].");
    log.debug("Event payload {}.", paymentReceived);
    log.info("Successfully processed event [PaymentReceivedV1].");
  }
}
