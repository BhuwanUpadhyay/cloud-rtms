package io.github.bhuwanupadhyay.rtms.ddd;

import lombok.Builder;

import java.util.*;
import java.util.function.Consumer;

@Builder
public class Result<R> {

  private final R result;
  private final List<DomainError> domainErrors;

  public void ifOk(Consumer<R> o) {
    if (isOk() && Objects.nonNull(result)) {
      o.accept(result);
    }
  }

  public List<DomainError> getDomainErrors() {
    return Collections.unmodifiableList(
        Optional.ofNullable(this.domainErrors).orElseGet(ArrayList::new));
  }

  public Optional<R> getResult() {
    return Optional.ofNullable(result);
  }

  public boolean isOk() {
    return Optional.ofNullable(domainErrors).map(List::isEmpty).orElse(true);
  }
}
