package zhy2002.springexamples.validation;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.testng.annotations.Test;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.Date;

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
        ShoppingCart cart = new ShoppingCart();
        cart.setDateCreated(new Date());
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(new Product());
        item.getProduct().setStock(new BigDecimal("1"));
        item.setQuantity(new BigDecimal("2"));
        cart.getItems().add(item);
        Validator validator = applicationContext.getBean(Validator.class);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(cart, "shoppingCart");

        //action
        validator.validate(cart, bindingResult);

        //assertion
        assertThat(bindingResult.hasErrors(), equalTo(true));
        assertThat(bindingResult.getAllErrors().stream().filter(objectError -> objectError.getDefaultMessage().equals("{invalid.shoppingcart}")).toArray().length, equalTo(1));


    }
}
