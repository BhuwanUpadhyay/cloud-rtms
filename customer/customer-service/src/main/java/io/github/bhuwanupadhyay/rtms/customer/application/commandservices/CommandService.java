package io.github.bhuwanupadhyay.rtms.customer.application.commandservices;

import io.github.bhuwanupadhyay.rtms.customer.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.customer.domain.commands.AppWorkflowCommand;

public interface CommandService<R> {

  R create(AppCreateCommand command);

  R workflow(AppWorkflowCommand command);
}
