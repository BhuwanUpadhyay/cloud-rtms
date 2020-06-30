package io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates;

import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.ddd.AggregateRoot;
import io.github.bhuwanupadhyay.rtms.ddd.DomainAsserts;
import io.github.bhuwanupadhyay.rtms.ddd.DomainError;
import io.github.bhuwanupadhyay.rtms.ddd.DomainException;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.events.InventoryCreated;
import io.github.bhuwanupadhyay.rtms.inventory.domain.events.WorkflowExecuted;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.InventoryDb;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.*;
import io.github.bhuwanupadhyay.rtms.rules.Problem;
import io.github.bhuwanupadhyay.rtms.rules.Result;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("ConstantConditions")
@Entity
@Table(name = InventoryDb.TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
@Slf4j
public class Inventory extends AggregateRoot<InventoryId> {

  @Embedded
  @Valid
  private InventoryName inventoryName;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<@Valid ProductLine> productLines;

  @Enumerated(EnumType.STRING)
  @Column(name = InventoryDb.STATUS)
  private InventoryStatus status;

  @ElementCollection(fetch = FetchType.LAZY)
  private List<@Valid UserComment> userComments;

  public Inventory(InventoryId inventoryId) {
    super(inventoryId);
  }

  public Result<Inventory> execute(InventoryCreateCommand command) {
    log.debug("Params => {}", command);
    List<Problem> problems = new ArrayList<>();
    this.inventoryName = command.getInventoryName();
    this.productLines = new HashSet<>();
    this.userComments = new ArrayList<>();
    this.productLines.addAll(command.getProductLines());
    this.status = InventoryStatus.CREATED;
    this.setCreatedAt(LocalDateTime.now());
    this.registerEvent(
        InventoryCreated.builder()
            .inventoryId(this.getId().getReference())
            .inventoryName(this.getInventoryName().getName())
            .status(this.getStatus().name())
            .build());
    log.info("Executed {} {}", command.getClass().getName(), command);
    return Result.<Inventory>builder().result(this).problems(problems).build();
  }

  public Result<Inventory> execute(WorkflowCommand command) {
    log.debug("Params => {}", command);

    List<Problem> problems = new ArrayList<>();

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

    return Result.<Inventory>builder().result(this).problems(problems).build();
  }
}
