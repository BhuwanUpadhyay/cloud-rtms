//package io.github.bhuwanupadhyay.rtms.gateway;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@Profile({"prod"})
//public class SecurityConfig {
//
//  @Bean
//  public SecurityWebFilterChain springSecurityFilterChain(
//      ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {
//    // Authenticate through configured OpenID Provider
//    http.httpBasic().disable().formLogin().disable().oauth2Login();
//    // Also logout at the OpenID Connect provider
//    http.logout(
//        logout ->
//            logout.logoutSuccessHandler(
//                new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));
//    // Require authentication for all requests
//    http.authorizeExchange()
//        .pathMatchers("/actuator/health")
//        .permitAll()
//        .anyExchange()
//        .authenticated();
//    // Disable CSRF in the gateway to prevent conflicts with proxied service CSRF
//    http.csrf().disable();
//    return http.build();
//  }
//}
