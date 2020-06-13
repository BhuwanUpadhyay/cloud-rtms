package io.github.bhuwanupadhyay.rtms.order.application.commandservices;

import io.github.bhuwanupadhyay.rtms.order.domain.commands.AppCreateCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.commands.AppWorkflowCommand;
import io.github.bhuwanupadhyay.rtms.order.domain.model.aggregates.App;
import io.github.bhuwanupadhyay.rtms.order.domain.model.valueobjects.AppId;
import io.github.bhuwanupadhyay.rtms.order.infrastructure.repositories.jpa.AppDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppCommandService implements CommandService<AppId> {

  private final AppDomainRepository domainRepository;

  @Override
  @Transactional
  public AppId create(AppCreateCommand command) {
    final AppId appId = domainRepository.nextId();
    final App app = new App(appId, command);
    this.domainRepository.save(app);
    return appId;
  }

  @Override
  @Transactional
  public AppId workflow(AppWorkflowCommand command) {
    final AppId appId = new AppId(command.getAppId());
    App app = domainRepository.find(appId);
    app.executeWorkflow(command);
    this.domainRepository.save(app);
    return appId;
  }
}
