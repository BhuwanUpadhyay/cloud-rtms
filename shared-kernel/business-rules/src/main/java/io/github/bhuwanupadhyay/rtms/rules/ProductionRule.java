package io.github.bhuwanupadhyay.rtms.rules;

/**
 * Production rules are the heart of the system. So far, the command has traveled through different
 * stages which should ensure that the provided request can be processed. Production rules specified
 * the actions the system must perform to achieve the desired state. They deal with the task a
 * client is trying to accomplish. Using the MakeReservation command as a reference, they make the
 * necessary changes to register the requested resource as reserved. As a consequence of these
 * modifications, entities and domain services can generate events that let the client and other
 * systems know that a change has occurred.
 */
public interface ProductionRule {}
