package io.github.bhuwanupadhyay.rtms.customer.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.*;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class ReleaseVersion extends ValueObject {

  @Embedded private ReleaseId releaseId;
  @Embedded private ReleaseInfo releaseInfo;

  public ReleaseVersion(ReleaseId releaseId, ReleaseInfo releaseInfo) {
    this.releaseId = releaseId;
    this.releaseInfo = releaseInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReleaseVersion that = (ReleaseVersion) o;
    return Objects.equals(releaseId, that.releaseId)
        && Objects.equals(releaseInfo, that.releaseInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(releaseId, releaseInfo);
  }
}
