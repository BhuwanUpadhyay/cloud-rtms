package io.github.bhuwanupadhyay.rtms.rules;

/**
 * Syntax rule means that the command makes sense in the context of the domain. This validation
 * guarantees that the provided data is coherent for the system. One example of this validation is
 * for the start date for making a reservation - its value shouldn’t be in the past, and cannot be
 * null.
 */
public interface SyntaxRule {
  String IsRequired = "IsRequired";
}
