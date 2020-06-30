package io.github.bhuwanupadhyay.rtms.inventory.domain.commands;

import io.github.bhuwanupadhyay.rtms.command.CreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryName;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.ProductLine;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

@Builder
@Getter
@ToString
public class InventoryCreateCommand implements CreateCommand {
  @Valid
  private final InventoryName inventoryName;
  private final List<@Valid ProductLine> productLines;
}
