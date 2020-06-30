package io.github.bhuwanupadhyay.rtms.rules;

import org.assertj.core.api.AbstractAssert;

public final class ProblemAssertions {

  public static <T> ProblemAssert<T> assertThat(Result<T> root) {
    return new ProblemAssert<>(root);
  }

  @SuppressWarnings("UnusedReturnValue")
  public static class ProblemAssert<T>
      extends AbstractAssert<ProblemAssert<T>, Result<T>> {

    private ProblemAssert(Result<T> result) {
      super(result, ProblemAssert.class);
    }

    public ProblemAssert<T> hasProblems() {
      if (this.actual.isOk()) {
        failWithMessage("Problems does not occurred.");
      }
      return this;
    }

    public ProblemAssert<T> hasNoProblems() {
      if (this.actual.isBad()) {
        failWithMessage("Problems are occurred.");
      }
      return this;
    }

    public ProblemAssert<T> hasError(String propertyPath, String message) {
      this.hasProblems();

      boolean notExists = this.actual.getProblems().stream().noneMatch(problem -> problem.isEqualTo(propertyPath, message));

      if (notExists) {
        failWithMessage("No such error exists: <%s.%s>.", propertyPath, message);
      }
      return this;
    }
  }
}
