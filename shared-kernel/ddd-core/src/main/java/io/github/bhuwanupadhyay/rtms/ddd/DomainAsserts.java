package io.github.bhuwanupadhyay.rtms.ddd;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class DomainAsserts {

  private DomainAsserts() {}

  public static <T> DomainAssert<T> begin(T value) {
    return new DomainAssert<>(value);
  }

  private static <T> DomainAssert<T> begin(T value, List<DomainError> errors) {
    return new DomainAssert<>(value, errors);
  }

  public static class DomainAssert<T> {
    private final List<DomainError> domainErrors = new ArrayList<>();

    private final T actual;

    private DomainAssert(T value) {
      this(value, new ArrayList<>());
    }

    private DomainAssert(T value, List<DomainError> domainErrors) {
      this.actual = value;
      this.domainErrors.addAll(domainErrors);
    }

    public DomainAssert<T> notNull(Supplier<DomainError> error) {
      isNotNull(error);
      return this;
    }

    public DomainAssert<T> notBlank(Supplier<DomainError> error) {
      final boolean notNull = isNotNull(error);

      if (notNull) {
        String s = (String) this.actual;

        if (s.isBlank()) {
          this.domainErrors.add(error.get());
        }
      }

      return this;
    }

    public DomainAssert<T> atLeastOneElement(Supplier<DomainError> error) {
      final boolean notNull = isNotNull(error);

      if (notNull) {
        List s = (List) this.actual;

        if (s.size() < 1) {
          this.domainErrors.add(error.get());
        }
      }

      return this;
    }

    public DomainAssert<T> raiseIf(Predicate<T> predicate, Supplier<DomainError> error) {
      final boolean notNull = isNotNull(error);

      if (notNull) {

        if (predicate.test(this.actual)) {
          this.domainErrors.add(error.get());
        }
      }

      return this;
    }

    public void end() {
      if (this.domainErrors.isEmpty()) {
        return;
      }
      throw new DomainException(this.domainErrors);
    }

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType"})
    public <S> DomainAssert<S> switchIfNotNull(Optional<S> value, Supplier<DomainError> error) {
      if (value.isEmpty()) {
        this.domainErrors.add(error.get());
        end();
      }
      return DomainAsserts.begin(value.get(), this.domainErrors);
    }

    private boolean isNotNull(Supplier<DomainError> error) {
      if (Objects.isNull(this.actual)) {
        this.domainErrors.add(error.get());
        return false;
      }
      return true;
    }
  }
}
