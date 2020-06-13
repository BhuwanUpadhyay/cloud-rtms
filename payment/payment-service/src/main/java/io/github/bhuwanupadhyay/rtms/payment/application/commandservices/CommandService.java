package io.github.bhuwanupadhyay.rtms.payment.application.commandservices;

import io.github.bhuwanupadhyay.rtms.payment.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.payment.domain.commands.AppWorkflowCommand;

public interface CommandService<R> {

  R create(AppCreateCommand command);

  R workflow(AppWorkflowCommand command);
}
