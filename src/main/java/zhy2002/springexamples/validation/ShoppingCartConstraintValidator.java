package zhy2002.springexamples.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.ShoppingCartItem;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Implementation.
 */
public class ShoppingCartConstraintValidator implements ConstraintValidator<ShoppingCartConstraint, ShoppingCart> {

    @Autowired
    private ConversionService conversionService;

    private Date firstOrderDate;

    @Override
    public void initialize(ShoppingCartConstraint constraintAnnotation) {
        firstOrderDate = conversionService.convert("2014-01-09", Date.class);
    }

    @Override
    public boolean isValid(ShoppingCart value, ConstraintValidatorContext context) {

        if(value == null)
            return true;

        if(firstOrderDate.compareTo(value.getDateCreated()) > 0)
            return false;

        for (ShoppingCartItem item : value.getItems()) {
            BigDecimal stock = item.getProduct().getStock();
            if(stock == null)
                continue;

            if(item.getQuantity().compareTo(stock) > 0)
                return false;
        }

        return true;
    }

}
