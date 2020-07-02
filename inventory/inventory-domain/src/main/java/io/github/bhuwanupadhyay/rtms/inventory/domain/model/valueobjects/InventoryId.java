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
public class InventoryId extends ValueObject {

  @Column(name = InventoryDb.REF_NO)
  @NotBlank(message = "{InventoryId.NotBlank.message}", groups = SyntaxRule.class)
  private String refNo;

  public InventoryId(String refNo) {
    this.refNo = refNo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InventoryId that = (InventoryId) o;
    return Objects.equals(refNo, that.refNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refNo);
  }
}
