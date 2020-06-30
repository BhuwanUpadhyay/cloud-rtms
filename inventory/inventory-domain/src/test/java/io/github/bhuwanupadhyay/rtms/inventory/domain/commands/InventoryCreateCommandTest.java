package io.github.bhuwanupadhyay.rtms.inventory.domain.commands;

import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.ProductId;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.ProductLine;
import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.Quantity;
import io.github.bhuwanupadhyay.rtms.rules.Result;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRule;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRuleAssertions;
import io.github.bhuwanupadhyay.rtms.rules.SyntaxRuleValidator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;


class InventoryCreateCommandTest {

  private SyntaxRuleValidator<InventoryCreateCommand> validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
        .configure()
        .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("ValidationMessages")))
        .buildValidatorFactory();
    this.validator = new SyntaxRuleValidator<>(factory.getValidator());
  }

  @Test
  void checkSyntaxRule() {
    List<ProductLine> productLines = new ArrayList<>();
    productLines.add(new ProductLine(new ProductId(null), new Quantity(null)));
    Result<InventoryCreateCommand> result = this.validator.validate(InventoryCreateCommand.builder().productLines(productLines).build());
    SyntaxRuleAssertions.assertThat(result).hasProblems()
        .hasError("inventoryName", SyntaxRule.IsRequired);
  }
}