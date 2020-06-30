package io.github.bhuwanupadhyay.rtms.inventory.domain.model.factories;

import io.github.bhuwanupadhyay.rtms.inventory.domain.commands.InventoryCreateCommand;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.aggregates.Inventory;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.InventoryId;
import io.github.bhuwanupadhyay.rtms.rules.Result;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRules;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.function.Supplier;

@Slf4j
public class InventoryFactory {

  private static final Validator VALIDATOR = Validation.byProvider(HibernateValidator.class)
      .configure()
      .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("ValidationMessages")))
      .buildValidatorFactory().getValidator();

  public static Result<Inventory> createNew(Supplier<InventoryId> nextId, InventoryCreateCommand command) {
    Result<InventoryCreateCommand> syntax = new SyntaxRules<InventoryCreateCommand>(VALIDATOR).apply(command);
    return syntax
        .ok()
        .map(c -> Result.onExecute(() -> new Inventory(nextId.get()).execute(c)))
        .orElseGet(() -> Result.<Inventory>builder().problems(syntax.getProblems()).build());

  }
}
