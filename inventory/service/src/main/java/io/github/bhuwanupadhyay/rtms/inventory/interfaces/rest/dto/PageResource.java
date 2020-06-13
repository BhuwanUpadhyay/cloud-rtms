package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Builder(builderClassName = "Builder")
@Value
@JsonDeserialize(builder = PageResource.Builder.class)
public class PageResource {
  private final List<InventoryResource> content;
  private final Integer number;
  private final Integer size;
  private final Long totalElements;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {}
}
