package io.github.bhuwanupadhyay.rtms.notification.infrastructure.config;

import io.github.bhuwanupadhyay.rtms.notification.infrastructure.aspect.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

  @Bean
  @Profile(AppConstants.SPRING_PROFILE_DEVELOPMENT)
  public LoggingAspect loggingAspect(Environment env) {
    return new LoggingAspect(env);
  }
}
