package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

  private final ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

  @Bean
  public Encoder feignEncoder() {
    return new SpringEncoder(messageConverters);
  }

  @Bean
  public Decoder feignDecoder() {
    return new SpringDecoder(messageConverters);
  }
//
//  @Bean
//  public RequestInterceptor requestInterceptor() {
//    return new BasicAuthRequestInterceptor("admin", "admin");
//  }
}