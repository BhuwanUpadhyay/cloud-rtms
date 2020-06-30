package io.github.bhuwanupadhyay.rtms.rules;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolation;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class BasicProblem implements Problem {

  @NonNull
  private final String beanClass;
  @NonNull
  private final String propertyPath;
  @NonNull
  private final String message;

  public static <T> BasicProblem create(ConstraintViolation<T> violation) {
    return new BasicProblem(violation.getRootBeanClass().getName(), violation.getPropertyPath().toString(), violation.getMessage());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BasicProblem that = (BasicProblem) o;
    return Objects.equals(beanClass, that.beanClass) &&
        Objects.equals(propertyPath, that.propertyPath) &&
        Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(beanClass, propertyPath, message);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " {" +
        "propertyPath='" + propertyPath + '\'' +
        ", message='" + message + '\'' +
        '}';
  }
}
