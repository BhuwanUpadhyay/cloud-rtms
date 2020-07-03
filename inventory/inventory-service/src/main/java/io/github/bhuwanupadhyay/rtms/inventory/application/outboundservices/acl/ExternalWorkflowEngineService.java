package io.github.bhuwanupadhyay.rtms.inventory.application.outboundservices.acl;

import io.github.bhuwanupadhyay.rtms.command.RegisterWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient.FormRequest;
import static io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient.WorkflowResponse;

@Service
@RequiredArgsConstructor
public class ExternalWorkflowEngineService {
  private static final String REF_NO = "refNo";
  private static final String SOURCE_SERVICE = "sourceService";
  private static final String SAVE_REQUEST = "saveRequest";
  private static final String PROCESS = "RtmsProcess";
  private final ExternalWorkflowEngineClient workflowEngineClient;

  public RegisterWorkflowCommand startWorkflow(InventoryId inventoryId) {
    Map<String, Object> vars = new HashMap<>();
    vars.put(REF_NO, inventoryId.getRefNo());
    vars.put(SOURCE_SERVICE, "Inventory");
    vars.put(SAVE_REQUEST, true);
    WorkflowResponse response = workflowEngineClient.startWorkflow(PROCESS, new FormRequest(vars));
    return RegisterWorkflowCommand
        .builder()
        .currentProcess("InventoryProcess")
        .currentTask("DataEntry")
        .processId(response.getId())
        .build();
  }

  public void submitTask(Inventory inventory) {
    List<TaskResponse> tasks = workflowEngineClient.getTasks(inventory.getWorkflowInfo().getProcessId());
    Optional<TaskResponse> taskResponse = tasks.stream().findFirst();
    if (taskResponse.isEmpty()) {
      throw new RuntimeException("");
    }
    TaskResponse response = taskResponse.get();
    Map<String, Object> vars = new HashMap<>();
    workflowEngineClient.submitForm(response.getId(), new FormRequest(vars));
  }

}
