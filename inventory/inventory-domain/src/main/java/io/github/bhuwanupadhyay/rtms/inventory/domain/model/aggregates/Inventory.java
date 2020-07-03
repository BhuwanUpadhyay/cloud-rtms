package io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates;

import io.github.bhuwanupadhyay.rtms.command.RegisterWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.core.Result;
import io.github.bhuwanupadhyay.rtms.ddd.AggregateRoot;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.events.InventoryCreated;
import io.github.bhuwanupadhyay.rtms.inventory.domain.events.WorkflowExecuted;
import io.github.bhuwanupadhyay.rtms.inventory.domain.events.WorkflowRegistered;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.InventoryDb;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.*;
import io.github.bhuwanupadhyay.rtms.rules.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
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
  private InventoryName inventoryName;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<ProductLine> productLines = new HashSet<>();

  @Enumerated(EnumType.STRING)
  @Column(name = InventoryDb.STATUS)
  private InventoryStatus status;

  @Embedded
  private WorkflowInfo workflowInfo;

  @ElementCollection(fetch = FetchType.LAZY)
  private List<UserComment> userComments = new ArrayList<>();

  public Inventory(InventoryId inventoryId) {
    super(inventoryId);
  }

  public Result<Inventory> execute(InventoryCreateCommand command) {
    log.debug("Params => {}", command);
    List<Problem> problems = new ArrayList<>();
    this.inventoryName = command.getInventoryName();
    this.productLines.addAll(command.getProductLines());
    this.status = InventoryStatus.INITIAL;
    this.setCreatedAt(LocalDateTime.now());
    this.registerEvent(new InventoryCreated(this));
    log.info("Executed {} {}", command.getClass().getName(), command);
    return Result.<Inventory>builder().result(this).problems(problems).build();
  }

  public Result<Inventory> execute(RegisterWorkflowCommand command) {
    log.debug("Params => {}", command);
    List<Problem> problems = new ArrayList<>();
    this.workflowInfo = new WorkflowInfo(command.getProcessId(), command.getCurrentProcess(), command.getCurrentTask(), WorkflowStatus.SAVED);
    this.registerEvent(new WorkflowRegistered(this.workflowInfo));
    log.info("Executed {} {}", command.getClass().getName(), command);
    return Result.<Inventory>builder().result(this).problems(problems).build();
  }

  public Result<Inventory> execute(WorkflowCommand command) {
    log.debug("Params => {}", command);
    List<Problem> problems = new ArrayList<>();
    this.userComments = Optional.ofNullable(this.userComments).orElseGet(ArrayList::new);
    this.userComments.add(new UserComment("SYSTEM", command.getAction(), command.getComment()));

    if (Actions.REPAIR.equals(command.getAction())) {
      String payload = command.getPayloadJson();
    }

    this.workflowInfo.onAction(command.getAction());
    this.registerEvent(new WorkflowExecuted(command.getAction(), this.getStatus().name()));
    log.debug("Executed {} {}", command.getClass().getName(), command);

    return Result.<Inventory>builder().result(this).problems(problems).build();
  }

  public Set<ProductLine> getProductLines() {
    return Collections.unmodifiableSet(this.productLines);
  }

  public List<UserComment> getUserComments() {
    return Collections.unmodifiableList(this.userComments);
  }

}
