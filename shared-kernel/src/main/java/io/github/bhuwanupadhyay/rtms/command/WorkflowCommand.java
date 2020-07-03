package io.github.bhuwanupadhyay.rtms.command;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WorkflowCommand implements Command {

  private final String reference;
  private final String taskId;
  private final String action;
  private final String confirm;
  private final String comment;
  private final String payloadJson;
}
