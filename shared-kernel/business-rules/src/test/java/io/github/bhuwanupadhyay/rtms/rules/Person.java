package io.github.bhuwanupadhyay.rtms.rules;

import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.github.bhuwanupadhyay.rtms.rules.SyntaxRule.IsRequired;

@RequiredArgsConstructor
class Person {

  @NotBlank(message = IsRequired, groups = {SyntaxRule.class})
  @Max(value = 50, message = IsRequired, groups = {SyntaxRule.class})
  private final String name;

}