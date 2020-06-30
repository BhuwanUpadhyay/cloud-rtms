package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
public class ValidationConfiguration {

  @Bean
  public Validator validator(MessageSource messageSource) {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.setValidationMessageSource(messageSource);
    return validator;
  }


  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
    bean.addBasenames("classpath:org.hibernate.validator.ValidationMessages", "classpath:ValidationMessages");
    bean.setDefaultEncoding("UTF-8");
    return bean;
  }
}
