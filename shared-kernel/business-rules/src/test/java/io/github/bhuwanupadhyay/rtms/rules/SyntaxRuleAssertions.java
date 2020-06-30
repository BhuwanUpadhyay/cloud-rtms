package io.github.bhuwanupadhyay.rtms.rules;

import org.assertj.core.api.AbstractAssert;

import java.util.Arrays;

public final class SyntaxRuleAssertions {

  public static <T> SyntaxRuleAssert<T> assertThat(Result<T> root) {
    return new SyntaxRuleAssert<>(root);
  }

  @SuppressWarnings("UnusedReturnValue")
  public static class SyntaxRuleAssert<T>
      extends AbstractAssert<SyntaxRuleAssert<T>, Result<T>> {

    private SyntaxRuleAssert(Result<T> result) {
      super(result, SyntaxRuleAssert.class);
    }

    public SyntaxRuleAssert<T> hasProblems() {
      if (this.actual.isOk()) {
        failWithMessage("Problems does not occurred.");
      }
      return this;
    }

    public SyntaxRuleAssert<T> hasNoProblems() {
      if (this.actual.isBad()) {
        failWithMessage("Problems are occurred.");
      }
      return this;
    }

    public SyntaxRuleAssert<T> hasError(String propertyPath, String message) {
      this.hasProblems();

      boolean notExists = this.actual.getProblems().stream().noneMatch(problem -> problem.isEqualTo(propertyPath, message));

      if (notExists) {
        failWithMessage("No such error exists: <%s.%s>.", propertyPath, message);
      }
      return this;
    }
  }
}
