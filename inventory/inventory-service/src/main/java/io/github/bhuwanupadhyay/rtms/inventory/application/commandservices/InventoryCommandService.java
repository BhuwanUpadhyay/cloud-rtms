package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryCommandService implements CommandService<InventoryId> {

  private final InventoryDomainRepository domainRepository;

  @Override
  @Transactional
  public InventoryId create(InventoryCreateCommand command) {
    final InventoryId inventoryId = domainRepository.nextId();
    final Inventory inventory = new Inventory(inventoryId, command);
    this.domainRepository.save(inventory);
    return inventoryId;
  }

  @Override
  @Transactional
  public InventoryId workflow(InventoryWorkflowCommand command) {
    final InventoryId inventoryId = new InventoryId(command.getInventoryId());
    Inventory inventory = domainRepository.find(inventoryId);
    inventory.executeWorkflow(command);
    this.domainRepository.save(inventory);
    return inventoryId;
  }
}
