package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
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
  public Encoder feignFormEncoder() {
    return new SpringFormEncoder(new SpringEncoder(messageConverters));
  }

  @Bean
  public Decoder feignFormDecoder() {
    return new SpringDecoder(messageConverters);
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return new BasicAuthRequestInterceptor("admin", "admin");
  }
}