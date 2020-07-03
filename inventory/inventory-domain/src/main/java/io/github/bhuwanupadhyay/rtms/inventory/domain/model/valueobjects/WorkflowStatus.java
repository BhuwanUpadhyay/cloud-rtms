package io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum WorkflowStatus {
  CREATED,
  REPAIRED,
  VERIFIED,
  ACTIVATED,
  REJECTED,
  SAVED,
  MOVED;

  public WorkflowStatus onAction(String action) {
    switch (action) {
      case Actions.REPAIR -> {
        return WorkflowStatus.REPAIRED;
      }
      case Actions.ACCEPT -> {
        return WorkflowStatus.ACTIVATED;
      }
      case Actions.REJECT -> {
        return WorkflowStatus.REJECTED;
      }
      case Actions.VERIFY -> {
        return WorkflowStatus.VERIFIED;
      }
      case Actions.SAVE_REQUEST, Actions.RE_SAVE_REQUEST -> {
        return WorkflowStatus.SAVED;
      }
      case Actions.REQUEST_PROCEED, Actions.PROCEED -> {
        return WorkflowStatus.MOVED;
      }
      default -> throw new IllegalStateException("Not support value: " + action);
    }
  }

  public boolean isEnded() {
    return Objects.equals(this, REJECTED) ||
        Objects.equals(this, ACTIVATED);
  }

  public boolean isOpened() {
    return !this.isEnded();
  }
}
