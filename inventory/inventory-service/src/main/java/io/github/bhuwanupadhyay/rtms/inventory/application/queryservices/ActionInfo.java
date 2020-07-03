package io.github.bhuwanupadhyay.rtms.inventory.application.queryservices;

import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.Actions;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.services.http.ExternalWorkflowEngineClient.TaskResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class ActionInfo {
  private final String taskId;
  private final String action;

  public static List<ActionInfo> create(TaskResponse response) {
    List<ActionInfo> actionInfos = new ArrayList<>();
    switch (response.getName()) {
      case "DataEntry" -> {
        actionInfos.add(new ActionInfo(response.getId(), Actions.RE_SAVE_REQUEST));
        actionInfos.add(new ActionInfo(response.getId(), Actions.REQUEST_PROCEED));
      }
      case "Verify" -> {
        actionInfos.add(new ActionInfo(response.getId(), Actions.ACCEPT));
        actionInfos.add(new ActionInfo(response.getId(), Actions.REJECT));
        actionInfos.add(new ActionInfo(response.getId(), Actions.REPAIR));
      }
    }
    return actionInfos;
  }
}
