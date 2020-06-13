package io.github.bhuwanupadhyay.rtms.customer.infrastructure.brokers.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface AppEventSource {

  String INPUT = "rtmsInChannel";

  @Output("rtmsOutChannel")
  MessageChannel rtmsOutput();

  @Input(INPUT)
  SubscribableChannel rtmsInput();
}
