package zhy2002.springexamples.databinding;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.testng.annotations.Test;
import zhy2002.springexamples.EmptyStringConfig;
import zhy2002.springexamples.domain.Customer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


@ContextConfiguration(classes = {DataBindingConfig.class})
public class BindingTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerValidator customerValidator;

    @Test
    public void springBeanShouldBeInjected(){

       assertThat(customerValidator, not(nullValue()));
    }

    @Test
    public void reservedNameShouldFailCustomerValidator(){

        //arrange
        Customer customer = new Customer();
        customer.setFirstName("Jon");
        customer.setLastName("Snow");
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(customer, "customer to validate");

        //action
        ValidationUtils.invokeValidator(customerValidator, customer, errors);

        //assertion
        assertThat(errors.getErrorCount(), greaterThan(0));

    }

    @Test
    public void nonReservedNameShouldPassCustomerValidator(){

        //arrange
        Customer customer = new Customer();
        customer.setFirstName("Ned");
        customer.setLastName("Stark");
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(customer, "customer to validate");

        //action
        ValidationUtils.invokeValidator(customerValidator, customer, errors);

        //assertion
        assertThat(errors.getErrorCount(), equalTo(0));
    }

    @Test
    public void nestedValidatorShouldBeAutowired(){

    }



}
