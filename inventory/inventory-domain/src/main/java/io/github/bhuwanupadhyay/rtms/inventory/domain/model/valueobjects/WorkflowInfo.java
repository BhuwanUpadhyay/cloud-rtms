package io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects;

import io.github.bhuwanupadhyay.rtms.inventory.domain.model.InventoryDb;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class WorkflowInfo {

  @Column(name = InventoryDb.PROCESS_ID)
  private String processId;
  @Column(name = InventoryDb.CURRENT_TASK)
  private String currentTask;
  @Column(name = InventoryDb.CURRENT_PROCESS)
  private String currentProcess;

  public WorkflowInfo(String processId, String currentProcess, String currentTask) {
    this.processId = processId;
    this.currentProcess = currentProcess;
    this.currentTask = currentTask;
  }
}
