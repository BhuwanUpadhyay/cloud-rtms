package io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum InventoryStatus {
  CREATED(List.of(Actions.REPAIR, Actions.SUBMIT)),
  REPAIRED(List.of(Actions.SUBMIT, Actions.BACK_REPAIR)),
  VERIFIED(List.of(Actions.ACCEPT, Actions.REJECT, Actions.SUSPEND)),
  SUBMITTED(List.of(Actions.APPROVE, Actions.BACK_REPAIR)),
  ACTIVATED(List.of(Actions.SUSPEND)),
  SUSPENDED(List.of(Actions.ACCEPT));

  private final List<String> nextActions;

  public InventoryStatus nextStatus(String action) {
    switch (action) {
      case Actions.REPAIR -> {
        return InventoryStatus.REPAIRED;
      }
      case Actions.SUBMIT -> {
        return InventoryStatus.SUBMITTED;
      }
      case Actions.ACCEPT -> {
        return InventoryStatus.ACTIVATED;
      }
      case Actions.REJECT, Actions.BACK_REPAIR -> {
        return InventoryStatus.CREATED;
      }
      case Actions.SUSPEND -> {
        return InventoryStatus.SUSPENDED;
      }
      case Actions.APPROVE -> {
        return InventoryStatus.VERIFIED;
      }
      default -> throw new IllegalStateException("Not support value: " + action);
    }
  }
}
