package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Variables {
  private final String refNo;
  private final String sourceService;
  private final String confirm;
  private final String action;
  private final String comment;
  private final String payloadJson;
}