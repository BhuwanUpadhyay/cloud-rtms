package io.github.bhuwanupadhyay.rtms.workflow.listener;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BaseDelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitiateTaskListener implements DelegateListener {


  @Override
  public void notify(BaseDelegateExecution execution) throws Exception {
  }

}
