package io.github.bhuwanupadhyay.rtms.payment.domain.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class InventoryWorkflowCommand {

  private final String inventoryId;
  private final String action;
  private final String comment;
  private final String payloadJson;
}
