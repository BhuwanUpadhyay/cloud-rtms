package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = WorkflowResource.WorkflowResourceBuilder.class)
public class WorkflowResource {
  private final String action;
  private final String payloadJson;
  private final String comment;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class WorkflowResourceBuilder {}
}
