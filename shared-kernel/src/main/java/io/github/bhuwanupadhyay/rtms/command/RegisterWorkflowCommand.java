package io.github.bhuwanupadhyay.rtms.command;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AssignProcessCommand {

  private final String processId;
  private final String currentTask;
  private final String currentProcess;
}
