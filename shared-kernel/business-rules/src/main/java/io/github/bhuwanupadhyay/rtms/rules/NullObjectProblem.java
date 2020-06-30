package io.github.bhuwanupadhyay.rtms.rules;

public class NullObjectProblem extends BasicProblem {
  public NullObjectProblem() {
    super("$.NullObject", "$.NullObject", SyntaxRule.IsRequired);
  }
}
