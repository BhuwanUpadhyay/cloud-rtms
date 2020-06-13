package io.github.bhuwanupadhyay.rtms.order.application.commandservices;

import io.github.bhuwanupadhyay.rtms.order.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.commands.AppWorkflowCommand;

public interface CommandService<R> {

  R create(AppCreateCommand command);

  R workflow(AppWorkflowCommand command);
}
