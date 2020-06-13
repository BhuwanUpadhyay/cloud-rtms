package io.github.bhuwanupadhyay.rtms.order.domain.model.aggregates;

import io.github.bhuwanupadhyay.rtms.ddd.AggregateRoot;
import io.github.bhuwanupadhyay.rtms.ddd.DomainAsserts;
import io.github.bhuwanupadhyay.rtms.ddd.DomainError;
import io.github.bhuwanupadhyay.rtms.ddd.DomainException;
import io.github.bhuwanupadhyay.rtms.order.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.commands.InventoryWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.events.InventoryCreated;
import io.github.bhuwanupadhyay.rtms.order.domain.events.WorkflowExecuted;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("ConstantConditions")
@Entity
@Table(name = "rtms_inventory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
@Slf4j
public class Inventory extends AggregateRoot<InventoryId> {

  @Embedded private InventoryName inventoryName;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<ProductLine> productLines;

  @Enumerated(EnumType.STRING)
  private InventoryStatus status;

  @ElementCollection(fetch = FetchType.LAZY)
  private List<UserComment> userComments;

  public Inventory(InventoryId inventoryId, InventoryCreateCommand command) {
    super(inventoryId);
    log.debug("Params => {} {}", inventoryId, command);

    DomainAsserts.begin(command)
        .notNull(DomainError.create(this, "InventoryCreateCommandIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(InventoryCreateCommand::getInventoryName),
            DomainError.create(this, "InventoryNameIsRequired"))
        .notBlank(DomainError.create(this, "InventoryNameIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(InventoryCreateCommand::getProductLines),
            DomainError.create(this, "ProductLinesIsRequired"))
        .atLeastOneElement(DomainError.create(this, "AtLeastOneProductLinesIsRequired"))
        .end();

    this.inventoryName = new InventoryName(command.getInventoryName());
    this.productLines = new HashSet<>();
    this.userComments = new ArrayList<>();
    this.productLines.addAll(command.getProductLines());
    this.status = InventoryStatus.CREATED;
    this.setCreatedAt(LocalDateTime.now());
    this.registerEvent(
        InventoryCreated.builder()
            .inventoryId(this.getId().getInventoryId())
            .inventoryName(this.getInventoryName().getName())
            .status(this.getStatus().name())
            .build());
    log.info("Executed {} {}", command.getClass().getName(), command);
  }

  public void executeWorkflow(InventoryWorkflowCommand command) {
    log.debug("Params => {}", command);

    DomainAsserts.begin(command)
        .notNull(DomainError.create(this, "InventoryWorkflowCommandIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(InventoryWorkflowCommand::getAction),
            DomainError.create(this, "WorkflowActionIsRequired"))
        .notBlank(DomainError.create(this, "WorkflowActionIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(InventoryWorkflowCommand::getComment),
            DomainError.create(this, "WorkflowActionCommentIsRequired"))
        .notBlank(DomainError.create(this, "WorkflowActionCommentIsRequired"))
        .end();

    if (!this.getStatus().getNextActions().contains(command.getAction())) {
      throw new DomainException(List.of(DomainError.create(this, "WorkflowActionIsInvalid").get()));
    }

    this.userComments = Optional.ofNullable(this.userComments).orElseGet(ArrayList::new);
    this.userComments.add(new UserComment("SYSTEM", command.getAction(), command.getComment()));

    if (Actions.REPAIR.equals(command.getAction())) {
      DomainAsserts.begin(command.getPayloadJson())
          .notBlank(DomainError.create(this, "WorkflowPayloadJsonIsRequiredForRepair"))
          .end();
      String payload = command.getPayloadJson();
    }

    this.status = this.getStatus().nextStatus(command.getAction());
    this.registerEvent(new WorkflowExecuted(command.getAction(), this.getStatus().name()));
    log.debug("Executed {} {}", command.getClass().getName(), command);
  }
}
