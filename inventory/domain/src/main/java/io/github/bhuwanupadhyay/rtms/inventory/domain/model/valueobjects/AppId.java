package io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.DomainAsserts;
import io.github.bhuwanupadhyay.rtms.ddd.DomainError;
import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class AppId extends ValueObject {
  private String appId;

  public AppId(String appId) {
    DomainAsserts.begin(appId).notBlank(DomainError.create(this, "AppIdIsRequired")).end();
    this.appId = appId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AppId that = (AppId) o;
    return Objects.equals(appId, that.appId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appId);
  }
}
