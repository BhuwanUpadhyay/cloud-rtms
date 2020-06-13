package io.github.bhuwanupadhyay.rtms.customer.domain.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AppWorkflowCommand {

  private final String appId;
  private final String action;
  private final String comment;
  private final String payloadJson;
}
