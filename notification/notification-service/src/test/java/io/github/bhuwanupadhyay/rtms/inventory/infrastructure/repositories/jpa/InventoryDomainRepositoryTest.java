package io.github.bhuwanupadhyay.rtms.order.infrastructure.repositories.jpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import io.github.bhuwanupadhyay.rtms.order.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.events.InventoryCreated;
import io.github.bhuwanupadhyay.rtms.order.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.ddd.DomainEventPublisher;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@EnableAutoConfiguration
@EntityScan(basePackageClasses = {Inventory.class})
@ContextConfiguration(classes = {InventoryDomainRepository.class})
@ExtendWith(SpringExtension.class)
class InventoryDomainRepositoryTest {

  @Autowired private InventoryDomainRepository repository;
  @MockBean private DomainEventPublisher domainEventPublisher;

  @Test
  void testSave() {
    // given
    InventoryCreateCommand command =
        InventoryCreateCommand.builder()
            .inventoryName("name")
            .productLines(
                List.of(
                    new ProductLine(
                        new ProductId("productId"), new Quantity(1))))
            .build();
    Inventory inventory = new Inventory(repository.nextId(), command);
    // when
    InventoryId inventoryId = repository.save(inventory);
    // then
    assertNotNull(inventoryId);
    verify(domainEventPublisher).publish(any(InventoryCreated.class));
  }

  @Test
  void testFindOne() {
    // given
    ProductLine productLine =
        new ProductLine(new ProductId("productId"), new Quantity(1));
    InventoryCreateCommand command =
        InventoryCreateCommand.builder().inventoryName("name").productLines(List.of(productLine)).build();
    Inventory inventory = new Inventory(repository.nextId(), command);
    InventoryId inventoryId = repository.save(inventory);
    // when
    Optional<Inventory> result = repository.findOne(inventoryId);
    // then
    assertNotNull(result);
    assertTrue(result.isPresent());
    Inventory actual = result.get();
    assertEquals(inventoryId, actual.getId());
    assertEquals(new InventoryName("name"), actual.getInventoryName());
    assertNotNull(actual.getCreatedAt());
    assertEquals(productLine, actual.getProductLines().stream().findFirst().get());
  }

  @Test
  void testNextId() {
    InventoryId inventoryId = repository.nextId();
    InventoryId nextInventoryId = repository.nextId();

    assertNotEquals(inventoryId, nextInventoryId);
  }
}
