package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.command.CommandService;
import io.github.bhuwanupadhyay.rtms.ddd.Result;
import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.factories.InventoryFactory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryCommandService
    implements CommandService<InventoryCreateCommand, InventoryId> {

  private final InventoryDomainRepository repository;

  @Override
  @Transactional
  public Result<InventoryId> create(InventoryCreateCommand command) {
    final InventoryId id = this.repository.nextId();
    final Result<Inventory> result = InventoryFactory.create(id, command);
    result.ifOk(this.repository::save);
    return Result.<InventoryId>builder().result(id).domainErrors(result.getDomainErrors()).build();
  }

  @Override
  @Transactional
  public Result<InventoryId> workflow(WorkflowCommand command) {
    final InventoryId id = new InventoryId(command.getReference());
    final Inventory inventory = repository.find(id);
    final Result<Inventory> result = inventory.execute(command);
    result.ifOk(this.repository::save);
    return Result.<InventoryId>builder().result(id).domainErrors(result.getDomainErrors()).build();
  }
}
