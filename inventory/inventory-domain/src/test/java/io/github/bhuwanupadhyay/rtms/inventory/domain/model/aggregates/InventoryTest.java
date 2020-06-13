package io.github.bhuwanupadhyay.rtms.order.domain.model.aggregates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bhuwanupadhyay.rtms.order.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.events.InventoryCreated;
import io.github.bhuwanupadhyay.rtms.ddd.DomainAssertions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InventoryTest {

  @Test
  void testEntityIdIsRequired() {
    DomainAssertions.assertThat(() -> new Inventory(null, null)).hasErrorCode("EntityIdIsRequired");
  }

  @Test
  void testCreateCommandIsRequired() {
    DomainAssertions.assertThat(() -> new Inventory(new InventoryId("1"), null))
        .hasErrorCode("CreateCommandIsRequired");
  }

  @Test
  void testNameIsRequired() {
    DomainAssertions.assertThat(() -> new Inventory(new InventoryId("1"), InventoryCreateCommand.builder().build()))
        .hasErrorCode("InventoryNameIsRequired");
    DomainAssertions.assertThat(
            () -> new Inventory(new InventoryId("1"), InventoryCreateCommand.builder().inventoryName("  ").build()))
        .hasErrorCode("InventoryNameIsRequired");
  }

  @Test
  void testProductLinesIsRequired() {
    DomainAssertions.assertThat(
            () -> new Inventory(new InventoryId("1"), InventoryCreateCommand.builder().inventoryName("name").build()))
        .hasErrorCode("ProductLinesIsRequired");
  }

  @Test
  void testAtLeastOneProductLinesIsRequired() {
    DomainAssertions.assertThat(
            () ->
                new Inventory(
                    new InventoryId("1"),
                    InventoryCreateCommand.builder()
                        .inventoryName("name")
                        .productLines(new ArrayList<>())
                        .build()))
        .hasErrorCode("ProductLinesIsRequired");
  }

  @Test
  void whenCreateAppThenShouldInitializeItProperly() {
    // given
    List<ProductLine> productLines = new ArrayList<>();
    productLines.add(
        new ProductLine(new ProductId("1"), new Quantity(1)));
    InventoryCreateCommand command =
        InventoryCreateCommand.builder().inventoryName("name").productLines(productLines).build();
    InventoryId inventoryId = new InventoryId("id");

    // when
    Inventory inventory = new Inventory(inventoryId, command);

    // then
    Assertions.assertEquals(new InventoryId("id"), inventory.getId());
    assertEquals(InventoryStatus.CREATED, inventory.getStatus());
    Assertions.assertEquals(new InventoryName(command.getInventoryName()), inventory.getInventoryName());
    DomainAssertions.assertThat(inventory).hasSizeOfEvents(1).hasEvent(InventoryCreated.class);
  }
}
