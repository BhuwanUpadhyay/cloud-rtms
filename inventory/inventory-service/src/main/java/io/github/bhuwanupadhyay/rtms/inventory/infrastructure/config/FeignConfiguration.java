package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

  @Bean
  public HttpMessageConverters httpMessageConverters() {
    return new HttpMessageConverters();
  }

}