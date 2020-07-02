package io.github.bhuwanupadhyay.rtms.workflow.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class EndListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution execution) throws Exception {

  }
}
