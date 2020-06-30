package io.github.bhuwanupadhyay.rtms.inventory.domain.model.factories;

import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.rules.Result;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRuleAssertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class InventoryFactoryTest {

  @Test
  void canCreateNew() {
    String reference = UUID.randomUUID().toString();
    Result<Inventory> result = InventoryFactory.createNew(() -> new InventoryId(reference), InventoryCreateCommand.builder().build());
    SyntaxRuleAssertions.assertThat(result).hasProblems();
  }
}