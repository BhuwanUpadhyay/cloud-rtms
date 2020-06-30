package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.command.CommandService;
import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryDomainRepository;
import io.github.bhuwanupadhyay.rtms.rules.Result;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.validation.Validator;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryCommandService
    implements CommandService<InventoryCreateCommand, InventoryId> {

  private static final Validator VALIDATOR = Validation.byProvider(HibernateValidator.class)
      .configure()
      .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("ValidationMessages")))
      .buildValidatorFactory().getValidator();

  private final InventoryDomainRepository repository;

  @Override
  @Transactional
  public Result<InventoryId> create(InventoryCreateCommand command) {
    Result<InventoryCreateCommand> syntax = new SyntaxRules<InventoryCreateCommand>(VALIDATOR).apply(command);

    Result<Inventory> result = syntax
        .ok()
        .map(c -> Result.onExecute(() -> new Inventory(this.repository.nextId()).execute(c)))
        .orElseGet(() -> Result.<Inventory>builder().problems(syntax.getProblems()).build());

    return savedResult(result);
  }

  @Override
  @Transactional
  public Result<InventoryId> workflow(WorkflowCommand command) {
    Result<WorkflowCommand> syntax = new SyntaxRules<WorkflowCommand>(VALIDATOR).apply(command);

    Result<Inventory> result = syntax
        .ok()
        .map(c -> Result.onExecute(() -> runWorkflow(c)))
        .orElseGet(() -> Result.<Inventory>builder().problems(syntax.getProblems()).build());

    return savedResult(result);
  }

  private Result<Inventory> runWorkflow(WorkflowCommand command) {
    return repository.find(new InventoryId(command.getReference())).execute(command);
  }

  private Result<InventoryId> savedResult(Result<Inventory> result) {
    return result
        .ok()
        .map(repository::save)
        .map(id -> Result.<InventoryId>builder().result(id).build())
        .orElseGet(() -> Result.<InventoryId>builder().problems(result.getProblems()).build());
  }
}
