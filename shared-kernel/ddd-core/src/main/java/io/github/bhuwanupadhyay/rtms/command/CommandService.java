package io.github.bhuwanupadhyay.rtms.command;

import io.github.bhuwanupadhyay.rtms.ddd.Result;
import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;

public interface CommandService<T extends CreateCommand, R extends ValueObject> {

  Result<R> create(T command);

  Result<R> workflow(WorkflowCommand command);
}
