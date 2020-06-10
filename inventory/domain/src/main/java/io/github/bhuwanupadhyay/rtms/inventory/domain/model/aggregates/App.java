package io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates;

import io.github.bhuwanupadhyay.rtms.ddd.AggregateRoot;
import io.github.bhuwanupadhyay.rtms.ddd.DomainAsserts;
import io.github.bhuwanupadhyay.rtms.ddd.DomainError;
import io.github.bhuwanupadhyay.rtms.ddd.DomainException;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.AppWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.events.AppCreated;
import io.github.bhuwanupadhyay.rtms.inventory.domain.events.WorkflowExecuted;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.*;
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
@Table(name = "RTMS_APP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
@Slf4j
public class App extends AggregateRoot<AppId> {

  @Embedded private AppName appName;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<ReleaseVersion> releaseVersions;

  @Enumerated(EnumType.STRING)
  private AppStatus status;

  @ElementCollection(fetch = FetchType.LAZY)
  private List<UserComment> userComments;

  public App(AppId appId, AppCreateCommand command) {
    super(appId);
    log.debug("Params => {} {}", appId, command);

    DomainAsserts.begin(command)
        .notNull(DomainError.create(this, "AppCreateCommandIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(AppCreateCommand::getAppName),
            DomainError.create(this, "AppNameIsRequired"))
        .notBlank(DomainError.create(this, "AppNameIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(AppCreateCommand::getReleaseVersions),
            DomainError.create(this, "ReleaseVersionsIsRequired"))
        .atLeastOneElement(DomainError.create(this, "AtLeastOneReleaseVersionsIsRequired"))
        .end();

    this.appName = new AppName(command.getAppName());
    this.releaseVersions = new HashSet<>();
    this.userComments = new ArrayList<>();
    this.releaseVersions.addAll(command.getReleaseVersions());
    this.status = AppStatus.CREATED;
    this.setCreatedAt(LocalDateTime.now());
    this.registerEvent(
        AppCreated.builder()
            .appId(this.getId().getAppId())
            .appName(this.getAppName().getName())
            .status(this.getStatus().name())
            .build());
    log.info("Executed {} {}", command.getClass().getName(), command);
  }

  public void executeWorkflow(AppWorkflowCommand command) {
    log.debug("Params => {}", command);

    DomainAsserts.begin(command)
        .notNull(DomainError.create(this, "AppWorkflowCommandIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(AppWorkflowCommand::getAction),
            DomainError.create(this, "WorkflowActionIsRequired"))
        .notBlank(DomainError.create(this, "WorkflowActionIsRequired"))
        .switchIfNotNull(
            Optional.ofNullable(command).map(AppWorkflowCommand::getComment),
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
