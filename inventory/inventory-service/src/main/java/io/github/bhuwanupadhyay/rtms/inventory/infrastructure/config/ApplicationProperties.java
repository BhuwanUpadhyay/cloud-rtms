package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to App.
 *
 * <p>Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Setter
@Getter
@ToString
public class ApplicationProperties {
  private WorkflowEngine workflowEngine;

  @Getter
  @Setter
  @ToString
  public static class WorkflowEngine {
    
    private String url;
    private String basePath;

  }
}
