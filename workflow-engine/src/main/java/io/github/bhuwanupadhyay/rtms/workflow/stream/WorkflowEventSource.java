package io.github.bhuwanupadhyay.rtms.workflow.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface WorkflowEventSource {

  @Output("workflowOutput")
  MessageChannel workflowOutput();

}
