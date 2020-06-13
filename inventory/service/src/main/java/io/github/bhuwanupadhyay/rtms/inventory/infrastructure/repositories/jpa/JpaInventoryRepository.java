package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa;

import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaInventoryRepository extends JpaRepository<Inventory, Long> {

  @Transactional(readOnly = true)
  @Query("select i from Inventory i where i.id = :id")
  Optional<Inventory> getById(@Param("id") InventoryId id);
}
