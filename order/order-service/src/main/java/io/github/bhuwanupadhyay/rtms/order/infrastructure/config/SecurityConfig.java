package io.github.bhuwanupadhyay.rtms.order.infrastructure.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

@EnableWebFluxSecurity
@Profile(AppConstants.SPRING_PROFILE_PRODUCTION)
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    // Validate tokens through configured OpenID Provider
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    // Require authentication for all requests
    http.authorizeExchange()
        .pathMatchers("/actuator/health")
        .permitAll()
        .anyExchange()
        .authenticated();
    return http.build();
  }

  private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
    ReactiveJwtAuthenticationConverter jwtAuthenticationConverter =
        new ReactiveJwtAuthenticationConverter();
    // Convert realm_access.roles claims to granted authorities, for use in access decisions
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
    return jwtAuthenticationConverter;
  }

  @Bean
  public ReactiveJwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties) {
    String issuerUri = properties.getJwt().getIssuerUri();
    NimbusReactiveJwtDecoder jwtDecoder =
        (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    // Use preferred_username from claims as authentication name, instead of UUID subject
    jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());
    return jwtDecoder;
  }
}

// As per:
// https://docs.spring.io/spring-security/site/docs/5.2.x/reference/html5/#oauth2resourceserver-jwt-claimsetmapping-rename
class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {

  private final MappedJwtClaimSetConverter delegate =
      MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

  @SuppressWarnings("NullableProblems")
  @Override
  public Map<String, Object> convert(Map<String, Object> claims) {
    Map<String, Object> convertedClaims = this.delegate.convert(claims);
    String username = (String) convertedClaims.get("preferred_username");
    convertedClaims.put("sub", username);
    return convertedClaims;
  }
}

class KeycloakRealmRoleConverter implements Converter<Jwt, Flux<GrantedAuthority>> {

  @Override
  @SuppressWarnings("unchecked")
  public Flux<GrantedAuthority> convert(final Jwt jwt) {
    final Map<String, Object> realmAccess =
        (Map<String, Object>) jwt.getClaims().get("realm_access");
    List<String> rolesString = (List<String>) realmAccess.get("roles");
    List<SimpleGrantedAuthority> roles =
        rolesString.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return Flux.fromIterable(roles);
  }
}
