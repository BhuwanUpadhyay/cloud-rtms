package io.github.bhuwanupadhyay.rtms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RtmsGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(RtmsGatewayApplication.class, args);
  }
}
