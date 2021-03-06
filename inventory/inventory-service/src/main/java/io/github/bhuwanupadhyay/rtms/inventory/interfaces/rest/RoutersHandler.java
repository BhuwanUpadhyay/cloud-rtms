package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest;

import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.inventory.application.commandservices.InventoryCommandService;
import io.github.bhuwanupadhyay.rtms.inventory.application.queryservices.WorkflowQueryService;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.*;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.config.ApplicationProperties;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryQueryRepository;
import io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto.*;
import io.github.bhuwanupadhyay.rtms.rules.ProblemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoutersHandler {
  private final InventoryQueryRepository queryRepository;
  private final InventoryCommandService commandService;
  private final WorkflowQueryService workflowQueryService;

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(InventoryRequest.class)
        .flatMap(
            r -> commandService.create(toCommand(r))
                .ok()
                .map(id -> ServerResponse.status(HttpStatus.CREATED).body(fromValue(toResource(id)))).orElseThrow(ProblemException::new)
        );
  }

  public Mono<ServerResponse> list(ServerRequest request) {
    int number =
        request.queryParam("number").map(Integer::parseInt).filter(num -> num > -1).orElse(0);
    int size = request.queryParam("size").map(Integer::parseInt).filter(num -> num > 0).orElse(20);
    Page<Inventory> page = queryRepository.findAll(PageRequest.of(number, size));
    List<InventoryResource> content =
        page.getContent().stream()
            .map(inventory -> toResource(inventory, List.of(linkOfGet(inventory.getId()))))
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
        .findOne(new InventoryId(id))
        .map(
            inventory ->
                toResource(inventory, linksOfGetTask(inventory)))
        .map(inventoryResource -> ServerResponse.ok().body(fromValue(inventoryResource)))
        .orElseGet(() -> ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> workflow(ServerRequest request) {
    return request
        .bodyToMono(WorkflowRequest.class)
        .flatMap(
            r -> commandService.workflow(toCommand(request, r))
                .ok()
                .map(id -> ServerResponse.status(HttpStatus.OK).body(fromValue(toResource(id)))).orElseThrow(ProblemException::new)
        );
  }

  private InventoryCreateCommand toCommand(InventoryRequest it) {
    return InventoryCreateCommand.builder()
        .inventoryName(new InventoryName(it.getName()))
        .productLines(
            it.getProductLines().stream()
                .map(
                    itt ->
                        new ProductLine(
                            new ProductId(itt.getProductId()), new Quantity(itt.getQuantity())))
                .collect(Collectors.toList()))
        .build();
  }

  private WorkflowCommand toCommand(ServerRequest request, WorkflowRequest resource) {
    return WorkflowCommand.builder()
        .reference(request.pathVariable("id"))
        .action(request.pathVariable("action"))
        .taskId(request.pathVariable("taskId"))
        .confirm(resource.getConfirm())
        .comment(resource.getComment())
        .payloadJson(resource.getPayloadJson())
        .build();
  }

  private InventoryIdResource toResource(InventoryId inventoryId) {
    return InventoryIdResource.builder()
        .inventoryId(inventoryId.getRefNo())
        .link(linkOfGet(inventoryId))
        .build();
  }

  private InventoryResource toResource(Inventory inventory, List<Link> links) {
    return InventoryResource.builder()
        .inventoryId(inventory.getId().getRefNo())
        .createdAt(iso().format(inventory.getCreatedAt()))
        .name(inventory.getInventoryName().getName())
        .productLines(
            inventory.getProductLines().stream()
                .map(
                    version ->
                        ProductLineResource.builder()
                            .quantity(version.getQuantity().getQuantity())
                            .productId(version.getProductId().getRefNo())
                            .build())
                .collect(Collectors.toList()))
        .links(links)
        .build();
  }

  private Link linkOfGet(InventoryId id) {
    return Link.builder().rel("self").method("GET").path("/inventories/" + id.getRefNo()).build();
  }

  private List<Link> linksOfGetTask(Inventory inventory) {
    WorkflowInfo workflowInfo = inventory.getWorkflowInfo();
    if (workflowInfo.getWorkflowStatus().isOpened()) {
      return workflowQueryService.getActions(workflowInfo)
          .stream()
          .map(info -> Link.builder()
              .rel(info.getAction()).method("PUT")
              .path("/inventories/" + inventory.getId().getRefNo() + "/tasks/" + info.getTaskId() + "/" + info.getAction()).build()).collect(Collectors.toList());
    }
    return List.of();
  }

  private DateTimeFormatter iso() {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  }
}
