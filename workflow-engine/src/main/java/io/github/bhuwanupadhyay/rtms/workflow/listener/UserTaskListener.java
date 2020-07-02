package io.github.bhuwanupadhyay.rtms.workflow.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class UserTaskListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution delegateExecution) throws Exception {

  }

}
