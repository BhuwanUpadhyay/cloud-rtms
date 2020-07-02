package io.github.bhuwanupadhyay.rtms.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

class SyntaxRulesTest {
  private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private SyntaxRules<Person> validator;

  @BeforeEach
  void setUp() {
    this.validator = new SyntaxRules<>(factory.getValidator());
  }

  @Test
  void canPreventNullObjectToValidate() {
    Result<Person> result = this.validator.apply(null);
    assertThat(result.isBad()).isTrue();
    assertThat(result.getProblems()).hasSize(1).first().isInstanceOf(NullObjectProblem.class);
  }

  @Test
  void testConstraints() {
    Result<Person> result = this.validator.apply(new Person(null));
    assertThat(result.isBad()).isTrue();
    assertThat(result.getProblems()).hasSize(1).first().isInstanceOf(BasicProblem.class);
  }
}