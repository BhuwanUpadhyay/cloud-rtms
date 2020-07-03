package io.github.bhuwanupadhyay.rtms.inventory;

import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableFeignClients
public class InventoryApplication {

  public static void main(String[] args) {
    SpringApplication.run(InventoryApplication.class, args);
  }
}
