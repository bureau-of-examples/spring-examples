package zhy2002.springexamples.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.testng.annotations.Test;
import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.ShoppingCart;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Spring EL tests.
 */
public class SpELTest {


    @Test
    public void basicSyntaxTests(){

        //string literal
        //==================================================================

        //arrange
        ExpressionParser parser = new SpelExpressionParser();

        //action
        Expression expression = parser.parseExpression("'Hello World'");
        String result = (String)expression.getValue();

        //assertion
        assertThat(result, equalTo("Hello World"));


        //method call
        //==================================================================

        //action
        expression = parser.parseExpression("'Hello World'.concat('!')");
        result = (String)expression.getValue();

        //assertion
        assertThat(result, equalTo("Hello World!"));


        //concatenation operator and eval against a root object
        //==================================================================

        //arrange
        Customer customer = new Customer();
        customer.setFirstName("Jon");
        customer.setLastName("Snow");

        //action
        expression = parser.parseExpression("firstName + ' ' + lastName");
        result = (String)expression.getValue(customer);

        //assertion
        assertThat(result, equalTo("Jon Snow"));


        //access property
        //==================================================================

        //arrange
        ShoppingCart cart = new ShoppingCart();
        cart.setCustomer(customer); //exception will be thrown if customer is null and we are access its properties

        //action
        expression = parser.parseExpression("customer.firstName");
        result = (String)expression.getValue(cart);

        //assertion
        assertThat(result, equalTo("Jon"));


        //can use default conversions
        //==================================================================

        //action
        expression = parser.parseExpression("'1999'");
        Integer integerResult = expression.getValue(Integer.class);

        //assertion
        assertThat(integerResult, equalTo(1999));


        //explicit root object overrides the one in EvaluationContext
        //==================================================================

        //arrange
        EvaluationContext evaluationContext = new StandardEvaluationContext(new ShoppingCart()); //customer is null

        //action
        expression = parser.parseExpression("customer");
        Customer customerResult = expression.getValue(evaluationContext, cart, Customer.class);

        //assertion
        assertThat(customerResult, sameInstance(customer));


    }

    //parked here: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#expressions-evaluation-context

    //SpEL in bean definition: #{ <expression string> }
}
