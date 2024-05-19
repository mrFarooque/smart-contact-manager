package com.smartcontactmanager.scm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ControllersConfig {
   @Bean
   public MessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
      messageSource.setBasenames("classpath:messages/messages");
      messageSource.setDefaultEncoding("UTF-8");
      return messageSource;
   }

   @Bean
   public BCryptPasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public WebClient webClient() {
      return WebClient.builder()
              .build();
   }
}
