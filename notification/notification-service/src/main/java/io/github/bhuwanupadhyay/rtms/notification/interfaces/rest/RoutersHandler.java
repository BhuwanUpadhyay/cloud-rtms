package io.github.bhuwanupadhyay.rtms.notification.interfaces.rest;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import io.github.bhuwanupadhyay.rtms.notification.application.commandservices.AppCommandService;
import io.github.bhuwanupadhyay.rtms.notification.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.notification.domain.commands.AppWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.notification.domain.model.aggregates.App;
import io.github.bhuwanupadhyay.rtms.notification.infrastructure.repositories.jpa.AppQueryRepository;
import io.github.bhuwanupadhyay.rtms.notification.interfaces.rest.dto.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import io.github.bhuwanupadhyay.rtms.notification.domain.model.valueobjects.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoutersHandler {
  private final AppQueryRepository queryRepository;
  private final AppCommandService commandService;

  public Mono<ServerResponse> getActions(ServerRequest request) {
    return ServerResponse.ok()
        .body(fromValue(Resource.builder()._link(linkOfCreate())._link(linkOfList()).build()));
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(CreateAppResource.class)
        .flatMap(
            it -> {
              AppCreateCommand command = toCommand(it);
              AppId appId = commandService.create(command);
              return ServerResponse.status(HttpStatus.CREATED)
                  .body(fromValue(toAppIdResource(appId)));
            });
  }

  public Mono<ServerResponse> list(ServerRequest request) {
    int number =
        request.queryParam("number").map(Integer::parseInt).filter(num -> num > -1).orElse(0);
    int size = request.queryParam("size").map(Integer::parseInt).filter(num -> num > 0).orElse(20);
    Page<App> page = queryRepository.findAll(PageRequest.of(number, size));
    List<AppResource> content =
        page.getContent().stream()
            .map(app -> toAppResource(app, List.of(linkOfGet(app.getId()))))
            .collect(Collectors.toList());
    return ServerResponse.ok()
        .body(
            fromValue(
                PageResource.builder()
                    .content(content)
                    .number(page.getNumber())
                    .size(page.getSize())
                    .totalElements(page.getTotalElements())
                    .build()));
  }

  public Mono<ServerResponse> get(ServerRequest request) {
    String id = request.pathVariable("id");
    return queryRepository
        .findOne(new AppId(id))
        .map(app -> toAppResource(app, linksOfWorkflow(app.getId(), app.getStatus())))
        .map(appResource -> ServerResponse.ok().body(fromValue(appResource)))
        .orElseGet(() -> ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> workflow(ServerRequest request) {
    return request
        .bodyToMono(WorkflowResource.class)
        .flatMap(
            it -> {
              AppWorkflowCommand command =
                  AppWorkflowCommand.builder()
                      .appId(request.pathVariable("id"))
                      .action(it.getAction())
                      .comment(it.getComment())
                      .payloadJson(it.getPayloadJson())
                      .build();
              AppId appId = commandService.workflow(command);
              return ServerResponse.ok().body(fromValue(toAppIdResource(appId)));
            });
  }

  private AppCreateCommand toCommand(CreateAppResource it) {
    return AppCreateCommand.builder()
        .appName(it.getName())
        .releaseVersions(
            it.getReleaseVersions().stream()
                .map(
                    itt ->
                        new ReleaseVersion(
                            new ReleaseId(itt.getReleaseId()),
                            new ReleaseInfo(LocalDateTime.from(iso().parse(itt.getDate())))))
                .collect(Collectors.toList()))
        .build();
  }

  private AppIdResource toAppIdResource(AppId appId) {
    return AppIdResource.builder().appId(appId.getAppId())._link(linkOfGet(appId)).build();
  }

  private AppResource toAppResource(App app, List<Link> links) {
    return AppResource.builder()
        .appId(app.getId().getAppId())
        .createdAt(iso().format(app.getCreatedAt()))
        .name(app.getAppName().getName())
        .releaseVersions(
            app.getReleaseVersions().stream()
                .map(
                    version ->
                        ReleaseVersionResource.builder()
                            .date(iso().format(version.getReleaseInfo().getDate()))
                            .releaseId(version.getReleaseId().getReleaseId())
                            .build())
                .collect(Collectors.toList()))
        ._links(links)
        .build();
  }

  private Link linkOfGet(AppId id) {
    return Link.builder().rel("get").method("GET").path("/apps/" + id.getAppId()).build();
  }

  private Link linkOfCreate() {
    return Link.builder().rel("create").method("POST").path("/apps").build();
  }

  private Link linkOfList() {
    return Link.builder().rel("list").method("GET").path("/apps").build();
  }

  private List<Link> linksOfWorkflow(AppId id, AppStatus status) {
    return status.getNextActions().stream()
        .map(
            action ->
                Link.builder()
                    .rel(action)
                    .method("PUT")
                    .path("/apps/" + id.getAppId() + "/" + action)
                    .build())
        .collect(Collectors.toList());
  }

  private DateTimeFormatter iso() {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  }
}
