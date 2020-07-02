package io.github.bhuwanupadhyay.rtms.rules;

import io.github.bhuwanupadhyay.rtms.core.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SyntaxRules<T> {
  @NonNull
  private final Validator validator;

  public Result<T> apply(T source) {
    if (Objects.isNull(source)) {
      return Result.<T>builder().problems(List.of(new NullObjectProblem())).build();
    } else {
      Set<ConstraintViolation<T>> violations = validator.validate(source, SyntaxRule.class);
      if (violations.isEmpty()) {
        return Result.<T>builder().result(source).build();
      }
      return Result.<T>builder().result(source).problems(violations.stream().map(BasicProblem::create).collect(Collectors.toList())).build();
    }
  }

}
