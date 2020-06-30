package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.command.CommandService;
import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.factories.InventoryFactory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryDomainRepository;
import io.github.bhuwanupadhyay.rtms.rules.Result;
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
    Result<Inventory> result = InventoryFactory.createNew(this.repository::nextId, command);
    return savedResult(result);
  }

  @Override
  @Transactional
  public Result<InventoryId> workflow(WorkflowCommand command) {
    Inventory inventory = repository.find(new InventoryId(command.getReference()));
    Result<Inventory> result = inventory.execute(command);
    return savedResult(result);
  }

  private Result<InventoryId> savedResult(Result<Inventory> result) {
    return result
        .ok()
        .map(repository::save)
        .map(id -> Result.<InventoryId>builder().result(id).build())
        .orElseGet(() -> Result.<InventoryId>builder().problems(result.getProblems()).build());
  }
}
