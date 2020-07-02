package io.github.bhuwanupadhyay.rtms.command;


import io.github.bhuwanupadhyay.rtms.rules.Result;

public interface CommandService<T extends CreateCommand, R> {

  Result<R> create(T command);

  Result<R> workflow(WorkflowCommand command);
}
