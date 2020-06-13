package io.github.bhuwanupadhyay.rtms.order.application.commandservices;

import io.github.bhuwanupadhyay.rtms.customer.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.customer.domain.commands.InventoryWorkflowCommand;

public interface CommandService<R> {

  R create(InventoryCreateCommand command);

  R workflow(InventoryWorkflowCommand command);
}
