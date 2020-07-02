package io.github.bhuwanupadhyay.rtms.inventory.application.commandservices;

import io.github.bhuwanupadhyay.rtms.inventory.application.outboundservices.acl.ExternalWorkflowEngineService;
import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.inventory.infrastructure.repositories.jpa.InventoryDomainRepository;
import io.github.bhuwanupadhyay.rtms.rules.ProblemAssertions;
import io.github.bhuwanupadhyay.rtms.core.Result;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;

@ExtendWith(MockitoExtension.class)
class InventoryCommandServiceTest {

  private final Validator validator = Validation.byProvider(HibernateValidator.class)
      .configure()
      .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("ValidationMessages")))
      .buildValidatorFactory().getValidator();

  private InventoryCommandService commandService;
  @Mock
  private InventoryDomainRepository repository;
  @Mock
  private ExternalWorkflowEngineService workflowEngineService;

  @BeforeEach
  void setUp() {
    this.commandService = new InventoryCommandService(validator, repository, workflowEngineService);
  }

  @Test
  void canRaiseSyntaxProblems() {
    Result<InventoryId> result = this.commandService.create(InventoryCreateCommand.builder().build());

    ProblemAssertions.assertThat(result).hasProblems()
        .hasError("inventoryName", "Inventory name is required.")
        .hasError("productLines", "Product lines is required.");
  }
}