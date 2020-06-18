package io.github.bhuwanupadhyay.rtms.order.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to App.
 *
 * <p>Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationProperties {}