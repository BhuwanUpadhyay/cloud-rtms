package io.github.bhuwanupadhyay.rtms.payment.infrastructure.repositories.jpa;

import io.github.bhuwanupadhyay.rtms.payment.domain.model.aggregates.App;
import io.github.bhuwanupadhyay.rtms.payment.domain.model.valueobjects.AppId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaAppRepository extends JpaRepository<App, Long> {

  @Transactional(readOnly = true)
  @Query("select i from App i where i.id = :id")
  Optional<App> getById(@Param("id") AppId id);
}
