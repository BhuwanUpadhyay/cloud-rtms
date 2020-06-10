package io.github.bhuwanupadhyay.rtms.inventory;

import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
@EnableConfigurationProperties(ApplicationProperties.class)
public class RtmsAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(RtmsAppApplication.class, args);
  }
}
