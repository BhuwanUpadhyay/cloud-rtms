package io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.InventoryDb;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRule;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Quantity extends ValueObject {

  @Column(name = InventoryDb.QUANTITY)
  @NotNull(message = "{Quantity.NotNull.message}", groups = SyntaxRule.class)
  @Positive(message = "{Quantity.Positive.message}", groups = SyntaxRule.class)
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
