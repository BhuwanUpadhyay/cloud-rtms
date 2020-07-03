package io.github.bhuwanupadhyay.rtms.inventory.application.outboundservices.acl;

import io.github.bhuwanupadhyay.rtms.command.RegisterWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class ExternalWorkflowEngineServiceTest {

  @Autowired
  private ExternalWorkflowEngineClient workflowEngineClient;
  private ExternalWorkflowEngineService workflowEngineService;

  @BeforeEach
  void setUp() {
    workflowEngineService = new ExternalWorkflowEngineService(workflowEngineClient);
  }

  @Test
  void startProcess() {
    RegisterWorkflowCommand command = workflowEngineService.startWorkflow(new InventoryId("ref-1"));
    Assertions.assertThat(command)
        .isNotNull();
  }
}