package io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.InventoryDb;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRule;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class ProductId extends ValueObject {

  @Column(name = InventoryDb.PRODUCT_REF_NO)
  @NotBlank(message = "{ProductId.NotBlank.message}", groups = SyntaxRule.class)
  private String refNo;

  public ProductId(String refNo) {
    this.refNo = refNo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductId productId = (ProductId) o;
    return Objects.equals(this.refNo, productId.refNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refNo);
  }
}
