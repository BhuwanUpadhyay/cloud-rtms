package io.github.bhuwanupadhyay.rtms.order.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class ReleaseId extends ValueObject {
  private String releaseId;

  public ReleaseId(String releaseId) {
    this.releaseId = releaseId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReleaseId releaseId = (ReleaseId) o;
    return Objects.equals(this.releaseId, releaseId.releaseId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(releaseId);
  }
}
