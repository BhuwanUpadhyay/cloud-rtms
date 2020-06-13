package io.github.bhuwanupadhyay.rtms.notification.application.commandservices;

import io.github.bhuwanupadhyay.rtms.notification.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.notification.domain.commands.AppWorkflowCommand;

public interface CommandService<R> {

  R create(AppCreateCommand command);

  R workflow(AppWorkflowCommand command);
}
