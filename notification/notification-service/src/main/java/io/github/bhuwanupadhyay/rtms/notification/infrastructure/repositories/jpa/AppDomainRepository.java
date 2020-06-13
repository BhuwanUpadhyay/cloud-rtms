package io.github.bhuwanupadhyay.rtms.notification.infrastructure.repositories.jpa;

import io.github.bhuwanupadhyay.rtms.notification.domain.model.aggregates.App;
import io.github.bhuwanupadhyay.rtms.notification.domain.model.valueobjects.AppId;
import io.github.bhuwanupadhyay.rtms.ddd.DomainEventPublisher;
import io.github.bhuwanupadhyay.rtms.ddd.DomainRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class AppDomainRepository extends DomainRepository<App, AppId> {

  private final JpaAppRepository repository;

  public AppDomainRepository(DomainEventPublisher publisher, JpaAppRepository repository) {
    super(publisher);
    this.repository = repository;
  }

  @Override
  public Optional<App> findOne(AppId appId) {
    return repository.getById(appId);
  }

  @Override
  protected void persist(App app) {
    repository.save(app);
  }

  @Override
  public AppId nextId() {
    return new AppId(UUID.randomUUID().toString());
  }
}
