package io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest;

import io.github.bhuwanupadhyay.rtms.ddd.DomainEntityNotFound;
import io.github.bhuwanupadhyay.rtms.rules.ProblemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class Routers {

  @Bean
  public RouterFunction<ServerResponse> restRouters(RoutersHandler handler) {
    return RouterFunctions.route()
        .POST("/inventories", handler::create)
        .GET("/inventories", handler::list)
        .GET("/inventories/{id}", handler::get)
        .PUT("/inventories/{id}/tasks/{taskId}/{action}", handler::workflow)
        .filter(
            (request, next) -> {
              try {
                return next.handle(request);
              } catch (DomainEntityNotFound ex) {
                logException(ex);
                return ServerResponse.notFound().build();
              } catch (ProblemException ex) {
                logException(ex);
                return ServerResponse.badRequest().build();
              } catch (Exception ex) {
                logException(ex);
                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
              }
            })
        .build();
  }

  private void logException(Exception ex) {
    log.error("{}", ex.getMessage());
    log.debug("Error while processing request.", ex);
  }
}
