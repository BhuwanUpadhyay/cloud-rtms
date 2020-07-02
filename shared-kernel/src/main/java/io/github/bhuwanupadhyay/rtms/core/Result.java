package io.github.bhuwanupadhyay.rtms.core;

import io.github.bhuwanupadhyay.rtms.rules.Problem;
import lombok.Builder;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Builder
public class Result<R> {

  private final R result;
  private final List<Problem> problems;

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

  public <T> Result<T> map(Function<R, Result<T>> func) {
    if (isOk()) {
      return func.apply(this.result);
    }
    return Result.<T>builder().problems(this.problems).build();
  }


  public boolean isOk() {
    return Optional.ofNullable(problems).map(List::isEmpty).orElse(true) && Objects.nonNull(result);
  }

  public boolean isBad() {
    return !isOk();
  }

  public Result<R> peek(Consumer<R> func) {
    if (isOk()) {
      func.accept(this.result);
      return Result.<R>builder().result(this.result).build();
    }
    return Result.<R>builder().problems(this.problems).build();
  }
}
