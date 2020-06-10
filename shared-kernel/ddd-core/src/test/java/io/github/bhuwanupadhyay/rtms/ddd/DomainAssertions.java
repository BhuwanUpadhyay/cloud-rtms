package io.github.bhuwanupadhyay.rtms.ddd;

import java.util.List;
import java.util.Objects;
import org.assertj.core.api.AbstractAssert;

public final class DomainAssertions {

  public static DomainValidationAssert assertThat(Runnable callback) {
    try {
      callback.run();
      return new DomainValidationAssert(null);
    } catch (DomainException e) {
      return new DomainValidationAssert(e);
    }
  }

  public static <T extends ValueObject> DomainEventAssert<T> assertThat(AggregateRoot<T> root) {
    return new DomainEventAssert<>(root);
  }

  public static class DomainEventAssert<T extends ValueObject>
      extends AbstractAssert<DomainEventAssert<T>, AggregateRoot<T>> {

    public DomainEventAssert(AggregateRoot<T> root) {
      super(root, DomainEventAssert.class);
    }

    public DomainEventAssert<T> hasNoEvents() {
      isNotNull();

      int size = this.actual.getDomainEvents().size();
      if (size != 0) {
        failWithMessage("Found total <%d> domain events but expected zero event.", size);
      }
      return this;
    }

    public DomainEventAssert<T> hasSizeOfEvents(int size) {
      isNotNull();

      int eventsSize = this.actual.getDomainEvents().size();

      if (eventsSize != size) {
        failWithMessage(
            "Found total <%d> domain events but expected <%d> events.", eventsSize, size);
      }

      return this;
    }

    public DomainEventAssert<T> hasEvent(Class<? extends DomainEvent> event) {
      isNotNull();

      String expectedEvent = event.getName();

      boolean noneMatch =
          this.actual.getDomainEvents().stream()
              .map(DomainEvent::getEventClassName)
              .noneMatch(s -> Objects.equals(s, expectedEvent));

      if (noneMatch) {
        failWithMessage("Aggregate does not have an event <%s>", expectedEvent);
      }

      return this;
    }
  }

  public static class DomainValidationAssert
      extends AbstractAssert<DomainValidationAssert, DomainException> {

    private DomainValidationAssert(DomainException exception) {
      super(exception, DomainValidationAssert.class);
    }

    private DomainValidationAssert hasErrors() {

      if (this.actual == null) {
        failWithMessage("Domain errors does not occurred.");
      }

      List<DomainError> domainErrors = this.actual.getDomainErrors();

      if (domainErrors.isEmpty()) {
        failWithMessage("Domain validation does not have any errors.");
      }
      return this;
    }

    public DomainValidationAssert hasNoErrors() {
      if (this.actual != null) {
        failWithMessage(
            "Found total <%d> domain errors but expected zero errors.%s",
            this.actual.getTotalErrors(), this.actual.getMessage());
      }

      return this;
    }

    public DomainValidationAssert hasErrorCode(String errorCode) {
      this.hasErrors();

      long count =
          this.actual.getErrorCodes().stream().filter(error -> error.endsWith(errorCode)).count();

      if (count != 1) {
        failWithMessage(
            "%s exists <%d> times on errors but expected <1> times.%s",
            errorCode, count, this.actual.getMessage());
      }
      return this;
    }
  }
}
