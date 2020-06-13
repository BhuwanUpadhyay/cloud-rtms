package io.github.bhuwanupadhyay.rtms.notification.domain.commands;

import io.github.bhuwanupadhyay.rtms.notification.domain.model.valueobjects.ReleaseVersion;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AppCreateCommand {

  private final String appName;
  private final List<ReleaseVersion> releaseVersions;
}
