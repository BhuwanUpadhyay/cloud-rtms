package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryWorkflowCommand;

public interface CommandService<R> {

  R create(InventoryCreateCommand command);

  R workflow(InventoryWorkflowCommand command);
}
