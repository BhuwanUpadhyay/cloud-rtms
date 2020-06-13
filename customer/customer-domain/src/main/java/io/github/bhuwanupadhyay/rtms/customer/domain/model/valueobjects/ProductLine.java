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
public class ProductLine extends ValueObject {

  @Embedded private ProductId productId;
  @Embedded private Quantity quantity;

  public ProductLine(ProductId productId, Quantity quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductLine that = (ProductLine) o;
    return Objects.equals(productId, that.productId)
        && Objects.equals(quantity, that.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, quantity);
  }
}
