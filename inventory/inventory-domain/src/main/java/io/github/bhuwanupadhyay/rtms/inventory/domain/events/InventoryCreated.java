package io.github.bhuwanupadhyay.rtms.inventory.domain.events;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEvent;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import lombok.Getter;

@Getter
public class InventoryCreated extends DomainEvent {

  private final String inventoryId;
  private final String inventoryName;
  private final String status;

  public InventoryCreated(Inventory inventory) {
    this.inventoryId = inventory.getId().getReference();
    this.inventoryName = inventory.getInventoryName().getName();
    this.status = inventory.getStatus().name();
  }
}
