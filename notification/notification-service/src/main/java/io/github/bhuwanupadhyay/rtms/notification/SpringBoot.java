package io.github.bhuwanupadhyay.rtms.notification;

import io.github.bhuwanupadhyay.rtms.notification.infrastructure.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringBoot {

  public static void main(String[] args) {
    SpringApplication.run(SpringBoot.class, args);
  }
}
