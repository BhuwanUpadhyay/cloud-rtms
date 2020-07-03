package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Builder(builderClassName = "Builder")
@Value
@JsonDeserialize(builder = InventoryRequest.Builder.class)
public class InventoryRequest {
  private String name;
  @Singular
  private List<ProductLineResource> productLines;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
