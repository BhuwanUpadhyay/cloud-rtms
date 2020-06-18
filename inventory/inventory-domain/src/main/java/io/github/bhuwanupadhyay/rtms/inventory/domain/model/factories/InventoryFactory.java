package io.github.bhuwanupadhyay.rtms.inventory.domain.model.factories;

import io.github.bhuwanupadhyay.rtms.ddd.DomainError;
import io.github.bhuwanupadhyay.rtms.ddd.Result;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class InventoryFactory {

  public static Result<Inventory> create(InventoryId inventoryId, InventoryCreateCommand command) {
    log.debug("Params => {} {}", inventoryId, command);
    List<DomainError> errors = new ArrayList<>();
    if (Objects.nonNull(command)){
      errors.add(DomainError.notNull("InventoryCreateCommand"));
    } else {

    }
    return Result.<Inventory>builder().domainErrors(errors).build();
  }
}
