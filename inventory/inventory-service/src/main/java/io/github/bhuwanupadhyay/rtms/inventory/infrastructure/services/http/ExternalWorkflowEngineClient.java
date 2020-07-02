package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http;

import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.dto.StartWorkflowResponse;
import org.springframework.stereotype.Service;

@Service
public class ExternalWorkflowEngineClient {

  public StartWorkflowResponse startWorkflow() {
    return new StartWorkflowResponse();
  }

}
