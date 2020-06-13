package io.github.bhuwanupadhyay.rtms.notification.interfaces.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = PageResource.PageResourceBuilder.class)
public class PageResource {
  private final List<AppResource> content;
  private final Integer number;
  private final Integer size;
  private final Long totalElements;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PageResourceBuilder {}
}
