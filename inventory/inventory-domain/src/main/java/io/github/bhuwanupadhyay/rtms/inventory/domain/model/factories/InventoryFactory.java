package io.github.bhuwanupadhyay.rtms.inventory.domain.model.factories;

import io.github.bhuwanupadhyay.rtms.ddd.Result;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryFactory {

  public static Result<Inventory> create(InventoryId inventoryId, InventoryCreateCommand command) {
    log.debug("Params => {} {}", inventoryId, command);
    Inventory inventory = new Inventory(inventoryId);
    return inventory.execute(command);
  }
}
