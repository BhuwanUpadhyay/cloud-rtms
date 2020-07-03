package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder(builderClassName = "Builder")
@Value
@JsonDeserialize(builder = WorkflowRequest.Builder.class)
public class WorkflowRequest {
  private final String confirm;
  private final String payloadJson;
  private final String comment;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
