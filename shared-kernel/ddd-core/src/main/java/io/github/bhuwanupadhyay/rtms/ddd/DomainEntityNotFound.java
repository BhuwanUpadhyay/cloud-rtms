package io.github.bhuwanupadhyay.rtms.ddd;

public final class DomainEntityNotFound extends RuntimeException {

  private final ValueObject id;

  public DomainEntityNotFound(String repository, ValueObject id) {
    super(String.format("[%s] Entity not found by id: %s", repository, id));
    this.id = id;
  }

  public ValueObject getId() {
    return id;
  }
}
