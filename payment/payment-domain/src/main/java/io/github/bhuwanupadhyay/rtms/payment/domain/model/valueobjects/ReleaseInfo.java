package io.github.bhuwanupadhyay.rtms.payment.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class ReleaseInfo extends ValueObject {
  private LocalDateTime date;

  public ReleaseInfo(LocalDateTime date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReleaseInfo releaseInfo = (ReleaseInfo) o;
    return Objects.equals(this.date, releaseInfo.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date);
  }
}
