package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;

@Configuration
public class FeignConfiguration {

  @Bean
  public Encoder feignEncoder() {
    ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(new FormHttpMessageConverter());
    return new SpringEncoder(objectFactory);
  }

  @Bean
  public Decoder feignDecoder() {
    return new SpringDecoder(HttpMessageConverters::new);
  }

}