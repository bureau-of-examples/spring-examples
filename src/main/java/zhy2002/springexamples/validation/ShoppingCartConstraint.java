package zhy2002.springexamples.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ensure all products in cart have enough stock.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=ShoppingCartConstraintValidator.class)
public @interface ShoppingCartConstraint {

    String message() default "{invalid.shoppingcart}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
