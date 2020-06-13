package io.github.bhuwanupadhyay.rtms.order.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder(builderClassName = "Builder")
@Value
@JsonDeserialize(builder = InventoryResource.Builder.class)
public class InventoryResource {
  private final String inventoryId;
  private final String name;
  @Singular private final List<ProductLineResource> productLines;
  private final String createdAt;
  @Singular private final List<Link> _links;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {}
}
