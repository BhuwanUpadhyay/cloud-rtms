package io.github.bhuwanupadhyay.rtms.order.infrastructure.brokers.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface InventoryEventSource {

  String INPUT = "rtmsInChannel";

  @Output("rtmsOutChannel")
  MessageChannel rtmsOutput();

  @Input(INPUT)
  SubscribableChannel rtmsInput();
}
