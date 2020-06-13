package io.github.bhuwanupadhyay.rtms.order.domain.commands;

import io.github.bhuwanupadhyay.rtms.order.domain.model.valueobjects.ProductLine;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class InventoryCreateCommand {

  private final String inventoryName;
  private final List<ProductLine> productLines;
}
