package io.github.bhuwanupadhyay.rtms.payment.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class AppName extends ValueObject {

  private String name;

  public AppName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AppName that = (AppName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
