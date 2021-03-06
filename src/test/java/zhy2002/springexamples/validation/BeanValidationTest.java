package zhy2002.springexamples.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.ValidationTestObject;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.ShoppingCartItem;
import zhy2002.springexamples.service.ShoppingCartService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test Spring validation behaviour.
 */
public class BeanValidationTest {

    @Test
    public void canUseBeanValidationInSpring(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("validationtest/enableBeanValidation.xml");
        Product product = new Product();
        product.setPrice(new BigDecimal("-109"));
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(product, "product");

        //action
        Validator validator = applicationContext.getBean(Validator.class);
        validator.validate(product, bindingResult);

        //assertion
        assertThat(bindingResult.hasErrors(), equalTo(true));
        int decimalMinCount = bindingResult.getAllErrors().stream().filter(objectError -> objectError.getCode().contains("DecimalMin")).toArray().length;
        assertThat(decimalMinCount, greaterThanOrEqualTo(1));
    }

    @Test
    public void beanValidatorIsAutomaticallyInjectedBySpring(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("validationtest/enableBeanValidation.xml");
        ShoppingCart cart = createOutOfStockCart();
        Validator validator = applicationContext.getBean(Validator.class);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(cart, "shoppingCart");

        //action
        validator.validate(cart, bindingResult);

        //assertion
        assertThat(bindingResult.hasErrors(), equalTo(true));
        assertThat(bindingResult.getAllErrors().stream().filter(objectError -> objectError.getDefaultMessage().equals("{invalid.shoppingcart}")).toArray().length, equalTo(1));

    }

    private ShoppingCart createOutOfStockCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setDateCreated(new Date());
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(new Product());
        item.getProduct().setStock(new BigDecimal("1"));
        item.setQuantity(new BigDecimal("2"));
        cart.getItems().add(item);
        return cart;
    }

    @Test
    public void methodLevelValidationShouldThrowConstraintViolationException(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("validationtest/springMethodValidation.xml");
        ShoppingCart cart = createOutOfStockCart();
        ShoppingCartService shoppingCartService = applicationContext.getBean(ShoppingCartService.class);

        //action
        try {
            shoppingCartService.save(cart);

            //assertion
            assertThat("Should have thrown exception", false);
        }catch (ConstraintViolationException ex){

            int invalidCartErrorCount =  ex.getConstraintViolations().stream().filter(constraintViolation -> constraintViolation.getMessage().equals("{invalid.shoppingcart}")).toArray().length;
            assertThat(invalidCartErrorCount, equalTo(1));
        }

    }

    @Test
    public void messageInterpolationShouldWorkWithDefaultValidationFactory(){

        //arrange
        ValidationTestObject testObject = createMessageInterpolationTestObject();
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        //action
        Set<ConstraintViolation<ValidationTestObject>> errors = validator.validate(testObject);
        ConstraintViolation<ValidationTestObject> error0 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan0")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error1 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan1")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error2 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan2")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error3 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan3")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error4 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan4")).findFirst().get();

        //assertion
        assertThat(error0.getMessage(), equalTo("must be greater than or equal to 1")); //default message
        assertThat(error1.getMessage(), equalTo("At least is 2.")); //passed-in static message
        assertThat(error2.getMessage(), equalTo("Cannot be smaller than 3.")); //passed-in message with annotation parameter
        assertThat(error3.getMessage(), equalTo("This integer cannot be less than 4.")); //message from validationMessages.properties with annotation parameter
        assertThat(error4.getMessage(), equalTo("Value 4 is not greater than or equal to 5.")); //message from validationMessages.properties with annotation parameter and EL expression.

    }

    @Test
    public void messageInterpolationShouldWorkWithSpringBeanValidationFactory(){
        //arrange
        ValidationTestObject testObject = createMessageInterpolationTestObject();
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("validationtest/enableBeanValidationWithMessageSource.xml");
        javax.validation.Validator validator = applicationContext.getBean(javax.validation.Validator.class);

        //action
        Set<ConstraintViolation<ValidationTestObject>> errors = validator.validate(testObject);
        ConstraintViolation<ValidationTestObject> error0 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan0")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error1 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan1")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error2 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan2")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error3 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan3")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error4 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan4")).findFirst().get();

        //assertion
        assertThat(error0.getMessage(), equalTo("must be greater than or equal to 1")); //default message
        assertThat(error1.getMessage(), equalTo("At least is 2.")); //passed-in static message
        assertThat(error2.getMessage(), equalTo("Cannot be smaller than 3.")); //passed-in message with annotation parameter
        assertThat(error3.getMessage(), equalTo("This integer cannot be less than 4.")); //message from validationMessages.properties with annotation parameter
        assertThat(error4.getMessage(), equalTo("Value 4 is not greater than or equal to 5.")); //message from validationMessages.properties with annotation parameter and EL expression.

    }

    @Test
    public void messageInterpolationShouldWorkWithSpringValidator(){
        //arrange
        ValidationTestObject testObject = createMessageInterpolationTestObject();
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("validationtest/enableBeanValidationWithMessageSource.xml");
        Validator validator = applicationContext.getBean(Validator.class);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(testObject, "testObj");

        //action
        validator.validate(testObject, errors);
        FieldError error0 = errors.getFieldError("greaterThan0");
        FieldError error1 = errors.getFieldError("greaterThan1");
        FieldError error2 = errors.getFieldError("greaterThan2");
        FieldError error3 = errors.getFieldError("greaterThan3");
        FieldError error4 = errors.getFieldError("greaterThan4");

        //assertion
        assertThat(applicationContext.getMessage(error0, LocaleContextHolder.getLocale()), equalTo("must be greater than or equal to 1")); //default message
        assertThat(applicationContext.getMessage(error1, LocaleContextHolder.getLocale()), equalTo("At least is 2.")); //passed-in static message
        assertThat(applicationContext.getMessage(error2, LocaleContextHolder.getLocale()), equalTo("Cannot be smaller than 3.")); //passed-in message with annotation parameter
        assertThat(applicationContext.getMessage(error3, LocaleContextHolder.getLocale()), equalTo("This integer cannot be less than 4.")); //message from validationMessages.properties with annotation parameter
        assertThat(applicationContext.getMessage(error4, LocaleContextHolder.getLocale()), equalTo("Value 4 is not greater than or equal to 5.")); //message from validationMessages.properties with annotation parameter and EL expression.
    }

    private ValidationTestObject createMessageInterpolationTestObject() {
        ValidationTestObject testObject = new ValidationTestObject();
        testObject.setGreaterThan0(0);
        testObject.setGreaterThan1(1);
        testObject.setGreaterThan2(2);
        testObject.setGreaterThan3(3);
        testObject.setGreaterThan4(4);
        return testObject;
    }

    //Hibernate interpolation how-to:
    //http://docs.jboss.org/hibernate/validator/5.2/reference/en-US/html_single/#chapter-message-interpolation


}
