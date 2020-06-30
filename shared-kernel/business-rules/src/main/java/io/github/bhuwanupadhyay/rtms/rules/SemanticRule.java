package io.github.bhuwanupadhyay.rtms.rules;

/**
 * Semantic validation relies on the system state. This type of rule intends to check whether the
 * provided command makes sense given the current state of the system, which is normally stored and
 * received through the system entities. For example, the MakeReservation command, one of such
 * validations is to check that another customer has not reserved the requested resource.
 */
public interface SemanticRule {


}
