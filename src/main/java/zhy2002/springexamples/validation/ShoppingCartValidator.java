package zhy2002.springexamples.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.ShoppingCartItem;

import java.util.HashSet;


public class ShoppingCartValidator implements Validator{

    public static final String ERROR_DUPLICATE_PRODUCT_ID = "shopping-cart.items.duplicate-product";

    @Autowired
    private CustomerValidator customerValidator;

    @Override
    public boolean supports(Class<?> clazz) {

        return clazz != null && ShoppingCart.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ShoppingCart cart = (ShoppingCart)target;
        ValidationUtils.invokeValidator(customerValidator, cart.getCustomer(), errors);

        HashSet<Long> productIds = new HashSet<>();
        for(ShoppingCartItem item : cart.getItems()){

            Long productId = item.getProduct().getId();
            if(productIds.contains(productId)){
                errors.reject(ERROR_DUPLICATE_PRODUCT_ID, new Object[]{productId}, ERROR_DUPLICATE_PRODUCT_ID);
                break;
            }
            productIds.add(productId);
        }

    }
}
