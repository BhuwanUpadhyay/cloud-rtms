package io.github.bhuwanupadhyay.rtms.order.domain.model.aggregates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bhuwanupadhyay.rtms.order.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.events.AppCreated;
import io.github.bhuwanupadhyay.rtms.order.domain.model.valueobjects.*;
import io.github.bhuwanupadhyay.rtms.ddd.DomainAssertions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {

  @Test
  void testEntityIdIsRequired() {
    DomainAssertions.assertThat(() -> new App(null, null)).hasErrorCode("EntityIdIsRequired");
  }

  @Test
  void testCreateCommandIsRequired() {
    DomainAssertions.assertThat(() -> new App(new AppId("1"), null))
        .hasErrorCode("CreateCommandIsRequired");
  }

  @Test
  void testAppNameIsRequired() {
    DomainAssertions.assertThat(() -> new App(new AppId("1"), AppCreateCommand.builder().build()))
        .hasErrorCode("AppNameIsRequired");
    DomainAssertions.assertThat(
            () -> new App(new AppId("1"), AppCreateCommand.builder().appName("  ").build()))
        .hasErrorCode("AppNameIsRequired");
  }

  @Test
  void testReleaseVersionsIsRequired() {
    DomainAssertions.assertThat(
            () -> new App(new AppId("1"), AppCreateCommand.builder().appName("name").build()))
        .hasErrorCode("ReleaseVersionsIsRequired");
  }

  @Test
  void testAtLeastOneReleaseVersionsIsRequired() {
    DomainAssertions.assertThat(
            () ->
                new App(
                    new AppId("1"),
                    AppCreateCommand.builder()
                        .appName("name")
                        .releaseVersions(new ArrayList<>())
                        .build()))
        .hasErrorCode("ReleaseVersionsIsRequired");
  }

  @Test
  void whenCreateAppThenShouldInitializeItProperly() {
    // given
    List<ReleaseVersion> releaseVersions = new ArrayList<>();
    releaseVersions.add(
        new ReleaseVersion(new ReleaseId("1"), new ReleaseInfo(LocalDateTime.now())));
    AppCreateCommand command =
        AppCreateCommand.builder().appName("name").releaseVersions(releaseVersions).build();
    AppId appId = new AppId("id");

    // when
    App app = new App(appId, command);

    // then
    assertEquals(new AppId("id"), app.getId());
    Assertions.assertEquals(AppStatus.CREATED, app.getStatus());
    assertEquals(new AppName(command.getAppName()), app.getAppName());
    DomainAssertions.assertThat(app).hasSizeOfEvents(1).hasEvent(AppCreated.class);
  }
}
