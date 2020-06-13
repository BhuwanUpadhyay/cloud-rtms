package io.github.bhuwanupadhyay.rtms.order.infrastructure.repositories.jpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import io.github.bhuwanupadhyay.rtms.order.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.events.AppCreated;
import io.github.bhuwanupadhyay.rtms.order.domain.model.aggregates.App;
import io.github.bhuwanupadhyay.rtms.ddd.DomainEventPublisher;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import io.github.bhuwanupadhyay.rtms.order.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@EnableAutoConfiguration
@EntityScan(basePackageClasses = {App.class})
@ContextConfiguration(classes = {AppDomainRepository.class})
@ExtendWith(SpringExtension.class)
class AppDomainRepositoryTest {

  @Autowired private AppDomainRepository repository;
  @MockBean private DomainEventPublisher domainEventPublisher;

  @Test
  void testSave() {
    // given
    AppCreateCommand command =
        AppCreateCommand.builder()
            .appName("name")
            .releaseVersions(
                List.of(
                    new ReleaseVersion(
                        new ReleaseId("productId"), new ReleaseInfo(LocalDateTime.now()))))
            .build();
    App app = new App(repository.nextId(), command);
    // when
    AppId appId = repository.save(app);
    // then
    assertNotNull(appId);
    verify(domainEventPublisher).publish(any(AppCreated.class));
  }

  @Test
  void testFindOne() {
    // given
    ReleaseVersion releaseVersion =
        new ReleaseVersion(new ReleaseId("productId"), new ReleaseInfo(LocalDateTime.now()));
    AppCreateCommand command =
        AppCreateCommand.builder().appName("name").releaseVersions(List.of(releaseVersion)).build();
    App app = new App(repository.nextId(), command);
    AppId appId = repository.save(app);
    // when
    Optional<App> result = repository.findOne(appId);
    // then
    assertNotNull(result);
    assertTrue(result.isPresent());
    App actual = result.get();
    assertEquals(appId, actual.getId());
    assertEquals(new AppName("name"), actual.getAppName());
    assertNotNull(actual.getCreatedAt());
    assertEquals(releaseVersion, actual.getReleaseVersions().stream().findFirst().get());
  }

  @Test
  void testNextAppId() {
    AppId appId = repository.nextId();
    AppId nextAppId = repository.nextId();

    assertNotEquals(appId, nextAppId);
  }
}
