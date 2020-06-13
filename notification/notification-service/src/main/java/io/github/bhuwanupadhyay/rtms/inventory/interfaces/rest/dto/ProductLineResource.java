package io.github.bhuwanupadhyay.rtms.order.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder(builderClassName = "Builder")
@Value
@JsonDeserialize(builder = ProductLineResource.Builder.class)
public class ProductLineResource {
  private final String productId;
  private final Integer quantity;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {}
}
