package zhy2002.springexamples.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.BeanPropertyBindingResult;
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
import java.sql.SQLSyntaxErrorException;
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
    public void test(){

        //arrange
        ValidationTestObject testObject = new ValidationTestObject();
        testObject.setGreaterThan0(0);
        testObject.setGreaterThan1(1);
        testObject.setGreaterThan2(2);
        testObject.setGreaterThan3(3);
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        //action
        Set<ConstraintViolation<ValidationTestObject>> errors = validator.validate(testObject);
        ConstraintViolation<ValidationTestObject> error0 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan0")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error1 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan1")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error2 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan2")).findFirst().get();
        ConstraintViolation<ValidationTestObject> error3 = errors.stream().filter(error -> error.getPropertyPath().toString().equals("greaterThan3")).findFirst().get();

        //assertion
        assertThat(error0.getMessage(), equalTo("must be greater than or equal to 1")); //default message
        assertThat(error1.getMessage(), equalTo("At least is 2.")); //passed-in static message
        assertThat(error2.getMessage(), equalTo("Cannot be smaller than 3.")); //passed-in message with annotation parameter
        assertThat(error3.getMessage(), equalTo("This integer cannot be less than 4.")); //message from validationMessages.properties with annotation parameter

        //parked here
        //http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#validation-beanvalidation-spring-other
        //http://docs.jboss.org/hibernate/validator/5.2/reference/en-US/html_single/#chapter-message-interpolation

//        Product product = new Product();
//        product.setPrice(new BigDecimal("-109"));
//
//
//        ConstraintViolation<Product> error = violations.iterator().next();
//
//        System.out.println(error);
//
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("validationtest/enableBeanValidation.xml");
//
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(product, "product");
//
//        //action
//        Validator validator = applicationContext.getBean(Validator.class);
//        validator.validate(product, bindingResult);
//
//        //assertion
//        assertThat(bindingResult.hasErrors(), equalTo(true));
    }
}
