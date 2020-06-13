package io.github.bhuwanupadhyay.rtms.order.infrastructure.repositories.jpa;

import io.github.bhuwanupadhyay.rtms.customer.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.customer.domain.model.valueobjects.InventoryId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InventoryQueryRepository {

  private final JpaInventoryRepository repository;

  public Optional<Inventory> findOne(InventoryId inventoryId) {
    return repository.getById(inventoryId);
  }

  public Page<Inventory> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
