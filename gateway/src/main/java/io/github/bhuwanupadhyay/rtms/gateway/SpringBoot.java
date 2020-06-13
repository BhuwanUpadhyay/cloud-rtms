package io.github.bhuwanupadhyay.rtms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBoot {

  public static void main(String[] args) {
    SpringApplication.run(SpringBoot.class, args);
  }
}
