package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "workflow-engine", path = "/camunda/api", url = "${app.workflow-engine-url}")
public interface ExternalWorkflowEngineClient {

  @PostMapping("/process-definition/{id}/start")
  WorkflowResponse startWorkflow(@PathVariable("id") String processName, @RequestBody FormRequest form);

  @GetMapping("/task")
  List<TaskResponse> getTasks(@RequestParam("processInstanceId") String processId);

  @PostMapping("/task/{id}/submit-form")
  void submitForm(@PathVariable("id") String taskId, @RequestBody FormRequest form);

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  class TaskResponse {
    private String id;
  }

  @Getter
  @RequiredArgsConstructor
  class FormRequest {
    private final Map<String, Object> variables;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  class WorkflowResponse {
    private String id;
  }

}
