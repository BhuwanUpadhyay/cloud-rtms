package io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa;

import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.ddd.DomainEventPublisher;
import io.github.bhuwanupadhyay.rtms.ddd.DomainRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryDomainRepository extends DomainRepository<Inventory, InventoryId> {

  private final JpaInventoryRepository repository;

  public InventoryDomainRepository(DomainEventPublisher publisher, JpaInventoryRepository repository) {
    super(publisher);
    this.repository = repository;
  }

  @Override
  public Optional<Inventory> findOne(InventoryId inventoryId) {
    return repository.getById(inventoryId);
  }

  @Override
  protected void persist(Inventory inventory) {
    repository.save(inventory);
  }

  @Override
  public InventoryId nextId() {
    return new InventoryId(UUID.randomUUID().toString());
  }
}
