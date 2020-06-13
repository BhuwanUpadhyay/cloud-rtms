package io.github.bhuwanupadhyay.rtms.payment.infrastructure.repositories.jpa;

import io.github.bhuwanupadhyay.rtms.payment.domain.model.aggregates.App;
import io.github.bhuwanupadhyay.rtms.payment.domain.model.valueobjects.AppId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppQueryRepository {

  private final JpaAppRepository repository;

  public Optional<App> findOne(AppId appId) {
    return repository.getById(appId);
  }

  public Page<App> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
