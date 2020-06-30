package io.github.bhuwanupadhyay.rtms.rules;

import java.util.Objects;

public interface Problem {
    String getMessage();

    String getPropertyPath();

    default boolean isEqualTo(String propertyPath, String message) {
        return Objects.equals(propertyPath, getPropertyPath()) && Objects.equals(message, getMessage());
    }
}
