package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest;

import io.github.bhuwanupadhyay.rtms.command.WorkflowCommand;
import io.github.bhuwanupadhyay.rtms.ddd.Result;
import io.github.bhuwanupadhyay.rtms.inventory.application.commandservices.InventoryCommandService;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.*;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryQueryRepository;
import io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto.*;
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

  public Mono<ServerResponse> getActions(ServerRequest request) {
    return ServerResponse.ok()
        .body(fromValue(Resource.builder()._link(linkOfCreate())._link(linkOfList()).build()));
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(CreateInventoryResource.class)
        .flatMap(
            it -> {
              InventoryCreateCommand command = toCommand(it);
              Result<InventoryId> inventoryId = commandService.create(command);
              return ServerResponse.status(HttpStatus.CREATED)
                  .body(fromValue(toResource(inventoryId.getResult().get())));
            });
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
                toResource(inventory, linksOfWorkflow(inventory.getId(), inventory.getStatus())))
        .map(inventoryResource -> ServerResponse.ok().body(fromValue(inventoryResource)))
        .orElseGet(() -> ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> workflow(ServerRequest request) {
    return request
        .bodyToMono(WorkflowResource.class)
        .flatMap(
            it -> {
              WorkflowCommand command =
                  WorkflowCommand.builder()
                      .reference(request.pathVariable("id"))
                      .action(it.getAction())
                      .comment(it.getComment())
                      .payloadJson(it.getPayloadJson())
                      .build();
              Result<InventoryId> inventoryId = commandService.workflow(command);
              return ServerResponse.ok().body(fromValue(toResource(inventoryId.getResult().get())));
            });
  }

  private InventoryCreateCommand toCommand(CreateInventoryResource it) {
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

  private InventoryIdResource toResource(InventoryId inventoryId) {
    return InventoryIdResource.builder()
        .inventoryId(inventoryId.getReference())
        ._link(linkOfGet(inventoryId))
        .build();
  }

  private InventoryResource toResource(Inventory inventory, List<Link> links) {
    return InventoryResource.builder()
        .inventoryId(inventory.getId().getReference())
        .createdAt(iso().format(inventory.getCreatedAt()))
        .name(inventory.getInventoryName().getName())
        .productLines(
            inventory.getProductLines().stream()
                .map(
                    version ->
                        ProductLineResource.builder()
                            .quantity(version.getQuantity().getQuantity())
                            .productId(version.getProductId().getProductId())
                            .build())
                .collect(Collectors.toList()))
        ._links(links)
        .build();
  }

  private Link linkOfGet(InventoryId id) {
    return Link.builder()
        .rel("get")
        .method("GET")
        .path("/inventories/" + id.getReference())
        .build();
  }

  private Link linkOfCreate() {
    return Link.builder().rel("create").method("POST").path("/inventories").build();
  }

  private Link linkOfList() {
    return Link.builder().rel("list").method("GET").path("/inventories").build();
  }

  private List<Link> linksOfWorkflow(InventoryId id, InventoryStatus status) {
    return status.getNextActions().stream()
        .map(
            action ->
                Link.builder()
                    .rel(action)
                    .method("PUT")
                    .path("/inventories/" + id.getReference() + "/" + action)
                    .build())
        .collect(Collectors.toList());
  }

  private DateTimeFormatter iso() {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  }
}
