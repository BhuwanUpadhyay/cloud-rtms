package io.github.bhuwanupadhyay.rtms.order.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder(builderClassName = "Builder")
@Value
@JsonDeserialize(builder = CreateInventoryResource.Builder.class)
public class CreateInventoryResource {
  private String name;
  @Singular private List<ProductLineResource> productLines;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {}
}
