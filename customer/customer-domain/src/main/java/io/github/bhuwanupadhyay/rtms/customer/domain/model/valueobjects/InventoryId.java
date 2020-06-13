package io.github.bhuwanupadhyay.rtms.customer.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.ddd.DomainAsserts;
import io.github.bhuwanupadhyay.rtms.ddd.DomainError;
import io.github.bhuwanupadhyay.rtms.ddd.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class InventoryId extends ValueObject {
  private String inventoryId;

  public InventoryId(String inventoryId) {
    DomainAsserts.begin(inventoryId).notBlank(DomainError.create(this, "InventoryIdIsRequired")).end();
    this.inventoryId = inventoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InventoryId that = (InventoryId) o;
    return Objects.equals(inventoryId, that.inventoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inventoryId);
  }
}
