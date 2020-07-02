package io.github.bhuwanupadhyay.rtms.rules;

import lombok.Builder;

import java.util.*;
import java.util.function.Supplier;

@Builder
public class Result<R> {

  private final R result;
  private final List<Problem> problems;

  public static <T> Result<T> onExecute(Supplier<Result<T>> command) {
    Result<T> result = command.get();
    return result.ok().map(e -> Result.<T>builder().result(e).build())
        .orElseGet(() -> Result.<T>builder().problems(result.getProblems()).build());
  }

  public List<Problem> getProblems() {
    return Collections.unmodifiableList(
        Optional.ofNullable(this.problems).orElseGet(ArrayList::new));
  }

  public Optional<R> ok() {
    if (isOk()) {
      return Optional.of(result);
    }
    return Optional.empty();
  }

  public boolean isOk() {
    return Optional.ofNullable(problems).map(List::isEmpty).orElse(true) && Objects.nonNull(result);
  }

  public boolean isBad() {
    return !isOk();
  }
}
