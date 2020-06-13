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
public class Quantity extends ValueObject {
  private Integer quantity;

  public Quantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Quantity quantity = (Quantity) o;
    return Objects.equals(this.quantity, quantity.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity);
  }
}
