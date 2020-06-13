package io.github.bhuwanupadhyay.rtms.notification.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = Resource.ResourceBuilder.class)
public class Resource {
  @Singular private final List<Link> _links;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class ResourceBuilder {}
}
