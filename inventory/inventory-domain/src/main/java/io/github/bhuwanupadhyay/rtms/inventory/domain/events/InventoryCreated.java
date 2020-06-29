package io.github.bhuwanupadhyay.rtms.inventory.domain.events;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryCreated extends DomainEvent {

  private final String inventoryId;
  private final String inventoryName;
  private final String status;

  private InventoryCreated(String inventoryId, String inventoryName, String status) {
    this.inventoryId = inventoryId;
    this.inventoryName = inventoryName;
    this.status = status;
  }
}
