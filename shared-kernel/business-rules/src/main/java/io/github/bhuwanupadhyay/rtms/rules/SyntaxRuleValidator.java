package io.github.bhuwanupadhyay.rtms.rules;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SyntaxRuleValidator<T> {
  @NonNull
  private final Validator validator;

  public Result<T> validate(T o) {
    if (Objects.isNull(o)) {
      return Result.<T>builder().problems(List.of(new NullObjectProblem())).build();
    } else {
      Set<ConstraintViolation<T>> violations = validator.validate(o, SyntaxRule.class);
      if (violations.isEmpty()) {
        return Result.<T>builder().result(o).build();
      }
      return Result.<T>builder().result(o).problems(violations.stream().map(BasicProblem::create).collect(Collectors.toList())).build();
    }
  }

}
