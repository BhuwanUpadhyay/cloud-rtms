package io.github.bhuwanupadhyay.rtms.workflow.listener;

import io.github.bhuwanupadhyay.rtms.workflow.stream.WorkflowEventSource;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BaseDelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.support.MessageBuilder.createMessage;

@Component
@RequiredArgsConstructor
public class InitiateTaskListener implements DelegateListener {

  private final WorkflowEventSource eventSource;

  @Override
  public void notify(BaseDelegateExecution execution) throws Exception {
    Map<String, Object> headers = new HashMap<>();
    WorkflowEvent event = new WorkflowEvent();
    eventSource.workflowOutput().send(createMessage(event, new MessageHeaders(headers)));
  }

}
