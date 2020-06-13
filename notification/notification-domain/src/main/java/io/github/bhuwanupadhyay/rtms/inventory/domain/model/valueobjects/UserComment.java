package io.github.bhuwanupadhyay.rtms.order.domain.model.valueobjects;

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
public class UserComment extends ValueObject {

  private LocalDateTime createdAt;
  private String username;
  private String action;
  private String comment;

  public UserComment(String username, String action, String comment) {
    this.username = username;
    this.comment = comment;
    this.action = action;
    this.createdAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserComment that = (UserComment) o;
    return Objects.equals(createdAt, that.createdAt)
        && Objects.equals(username, that.username)
        && Objects.equals(action, that.action)
        && Objects.equals(comment, that.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, username, action, comment);
  }
}
