package io.github.bhuwanupadhyay.rtms.notification.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = ReleaseVersionResource.ReleaseVersionResourceBuilder.class)
public class ReleaseVersionResource {
  private final String releaseId;
  private final String date;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class ReleaseVersionResourceBuilder {}
}
