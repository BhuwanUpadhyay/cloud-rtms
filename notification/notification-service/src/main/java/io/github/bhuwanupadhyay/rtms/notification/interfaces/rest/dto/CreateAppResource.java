package io.github.bhuwanupadhyay.rtms.notification.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = CreateAppResource.CreateAppResourceBuilder.class)
public class CreateAppResource {
  private String name;
  @Singular private List<ReleaseVersionResource> releaseVersions;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class CreateAppResourceBuilder {}
}
