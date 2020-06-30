package io.github.bhuwanupadhyay.rtms.inventory.domain.commands;

import io.github.bhuwanupadhyay.rtms.command.CreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryName;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.ProductLine;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRule;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@ToString
public class InventoryCreateCommand implements CreateCommand {
  @Valid
  @NotNull(message = "{InventoryCreateCommand.inventoryName.NotNull.message}", groups = SyntaxRule.class)
  private final InventoryName inventoryName;
  @NotNull(message = "{InventoryCreateCommand.productLines.NotNull.message}", groups = SyntaxRule.class)
  @Size(min = 1, message = "{InventoryCreateCommand.productLines.Size.message}", groups = SyntaxRule.class)
  private final List<@Valid ProductLine> productLines;
}
