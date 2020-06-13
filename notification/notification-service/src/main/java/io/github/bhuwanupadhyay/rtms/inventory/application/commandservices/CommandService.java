package io.github.bhuwanupadhyay.rtms.order.application.commandservices;

import io.github.bhuwanupadhyay.rtms.order.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.commands.InventoryWorkflowCommand;

public interface CommandService<R> {

  R create(InventoryCreateCommand command);

  R workflow(InventoryWorkflowCommand command);
}
