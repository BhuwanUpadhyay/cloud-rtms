package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Feign client accessing the methods of workflow engine.
 *
 * @see https://docs.camunda.org/manual/latest/reference/rest
 */
@FeignClient(name = "workflow-engine", url = "${app.workflow-engine-url}")
public interface ExternalWorkflowEngineClient {

  @PostMapping("/process-definition/key/{key}/start")
  WorkflowResponse startWorkflow(@PathVariable("key") String processName, @RequestBody FormRequest form);


  @GetMapping("/task")
  List<TaskResponse> getTasks(@RequestParam("processInstanceId") String processId);

  @PostMapping("/task/{id}/submit-form")
  void submitForm(@PathVariable("id") String taskId, @RequestBody FormRequest form);

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  class TaskResponse {
    private String id;
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Getter
  class FormValue {
    private final Object value;
    private final String type;

    public static FormValue ofBoolean(boolean v) {
      return new FormValue(v, "boolean");
    }
    
    public static FormValue ofString(String v) {
      return new FormValue(v, "string");
    }
  }

  @Getter
  @RequiredArgsConstructor
  class FormRequest {
    private final Map<String, FormValue> variables;


  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  class WorkflowResponse {
    private String id;
  }

}
