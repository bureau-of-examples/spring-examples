package zhy2002.springexamples.binding;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.testng.annotations.Test;
import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.Date;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test BeanWrapper behaviour.
 */
public class BeanWraperTest {

    private BeanWrapper createBeanWrapper(Object target){
        return new BeanWrapperImpl(target);
    }

    @Test
    public void canSetProperties(){

        //arrange
        ShoppingCart cart = new ShoppingCart();
        BeanWrapper wrapper = createBeanWrapper(cart);

        //action
        Date now = new Date();
        wrapper.setPropertyValue("dateCreated", now);
        wrapper.setPropertyValue("customer", new Customer()); //nested object cannot be null if we want to set nested object's property
        wrapper.setPropertyValue("customer.firstName", "Alex"); //nested property
        wrapper.setPropertyValue("customer.lastName", "Murphy");
        wrapper.setPropertyValue("customer.vip", true); //auto boxing
        wrapper.setPropertyValue("items[0]", new ShoppingCartItem());
        wrapper.setPropertyValue("items[0].product", new Product());
        wrapper.setPropertyValue("items[0].product.name", "Lego Star Wars");
        wrapper.setPropertyValue("items[0].quantity", new BigDecimal("5"));

        //assertion
        assertThat(cart.getDateCreated(), equalTo(now));
        assertThat(cart.getCustomer().getFirstName(), equalTo("Alex"));
        assertThat(cart.getCustomer().getLastName(), equalTo("Murphy"));
        assertThat(cart.getCustomer().getVip(), equalTo(true));
        assertThat(cart.getItems().size(), equalTo(1));
        assertThat(cart.getItems().get(0).getProduct().getName(), equalTo("Lego Star Wars"));
        assertThat(cart.getItems().get(0).getQuantity(), equalTo(new BigDecimal("5")));

    }



}
