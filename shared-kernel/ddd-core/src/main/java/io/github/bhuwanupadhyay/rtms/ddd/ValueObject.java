package io.github.bhuwanupadhyay.rtms.ddd;

public abstract class ValueObject {
  @Override
  public abstract int hashCode();

  @Override
  public abstract boolean equals(Object o);
}
