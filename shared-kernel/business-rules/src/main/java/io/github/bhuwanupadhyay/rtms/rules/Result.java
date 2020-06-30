package io.github.bhuwanupadhyay.rtms.rules;

import lombok.Builder;

import java.util.*;
import java.util.function.Consumer;

@Builder
public class Result<R> {

  private final R result;
  private final List<Problem> problems;

  public void ifOk(Consumer<R> o) {
    if (isOk() && Objects.nonNull(result)) {
      o.accept(result);
    }
  }

  public List<Problem> getProblems() {
    return Collections.unmodifiableList(
        Optional.ofNullable(this.problems).orElseGet(ArrayList::new));
  }

  public Optional<R> getResult() {
    return Optional.ofNullable(result);
  }

  public boolean isOk() {
    return Optional.ofNullable(problems).map(List::isEmpty).orElse(true);
  }

  public boolean isBad() {
    return !isOk();
  }
}
