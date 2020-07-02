package io.github.bhuwanupadhyay.rtms.command;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterWorkflowCommand {

  private final String processId;
  private final String currentTask;
  private final String currentProcess;
}
