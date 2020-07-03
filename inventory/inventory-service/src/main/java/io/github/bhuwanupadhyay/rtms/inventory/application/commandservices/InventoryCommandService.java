package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.command.CommandService;
import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.core.Result;
import io.github.bhuwanupadhyay.rtms.inventory.application.outboundservices.acl.ExternalWorkflowEngineService;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryDomainRepository;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryCommandService
    implements CommandService<InventoryCreateCommand, InventoryId> {

  private final Validator validator;
  private final InventoryDomainRepository repository;
  private final ExternalWorkflowEngineService workflowService;

  @Override
  @Transactional
  public Result<InventoryId> create(InventoryCreateCommand command) {
    Result<InventoryCreateCommand> syntax = new SyntaxRules<InventoryCreateCommand>(validator).apply(command);
    return syntax
        .map(c -> new Inventory(this.repository.nextId()).execute(c))
        .map(e -> e.execute(workflowService.startWorkflow(e.getId())))
        .peek(repository::save)
        .map(e -> Result.<InventoryId>builder().result(e.getId()).build());
  }

  @Override
  @Transactional
  public Result<InventoryId> workflow(WorkflowCommand command) {
    Result<WorkflowCommand> syntax = new SyntaxRules<WorkflowCommand>(validator).apply(command);
    return syntax
        .map(c -> repository.find(new InventoryId(c.getReference())).execute(c))
        .peek(e -> workflowService.submitTask(command))
        .peek(repository::save)
        .map(e -> Result.<InventoryId>builder().result(e.getId()).build());
  }

}
