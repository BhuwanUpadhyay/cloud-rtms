package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.AppWorkflowCommand;

public interface CommandService<R> {

  R create(AppCreateCommand command);

  R workflow(AppWorkflowCommand command);
}
