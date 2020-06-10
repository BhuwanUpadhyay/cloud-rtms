package io.github.bhuwanupadhyay.rtms.gateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iam")
public class IAmApi {

  @GetMapping("/already-exists")
  public ResponseEntity<Map<String, Object>> exist(@AuthenticationPrincipal OidcUser oidcUser) {
    Map<String, Object> map = new HashMap<>();
    map.put("exists", Objects.nonNull(oidcUser));
    return ResponseEntity.ok(map);
  }

  @GetMapping("/info")
  public ResponseEntity<Map<String, Object>> info(@AuthenticationPrincipal OidcUser oidcUser) {
    Map<String, Object> map = new HashMap<>();
    map.put("userName", oidcUser.getName());
    map.put("userAttributes", oidcUser.getAttributes());
    return ResponseEntity.ok(map);
  }
}
