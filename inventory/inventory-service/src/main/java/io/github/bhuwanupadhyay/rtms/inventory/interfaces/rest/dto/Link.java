package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = Link.LinkBuilder.class)
public class Link {
  private final String rel;
  private final String method;
  private final String path;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class LinkBuilder {}
}
