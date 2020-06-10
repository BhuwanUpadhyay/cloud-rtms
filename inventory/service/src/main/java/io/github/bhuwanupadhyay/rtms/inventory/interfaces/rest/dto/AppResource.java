package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = AppResource.AppResourceBuilder.class)
public class AppResource {
  private final String appId;
  private final String name;
  @Singular private final List<ReleaseVersionResource> releaseVersions;
  private final String createdAt;
  @Singular private final List<Link> _links;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class AppResourceBuilder {}
}
