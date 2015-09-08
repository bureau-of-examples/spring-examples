package zhy2002.springexamples.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import zhy2002.springexamples.domain.Customer;

import java.util.Objects;

/**
 *
 */
public class CustomerValidator implements Validator {

    public static final String  ERROR_RESERVED_NAME = "customer.reserved-name";

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz != null && Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null){
            return;
        }

        Customer customer = (Customer)target;
        if(Objects.equals(customer.getFirstName(), "Jon") && Objects.equals(customer.getLastName(), "Snow")){
            errors.reject(ERROR_RESERVED_NAME);
        }
    }
}
