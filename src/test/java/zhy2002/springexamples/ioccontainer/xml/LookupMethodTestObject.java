package zhy2002.springexamples.ioccontainer.xml;

import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.ShoppingCart;

/**
 * Lookup method test object.
 */
public abstract class LookupMethodTestObject {


    public ShoppingCart createCart(){

        return new ShoppingCart(createCustomer());
    }

    public ShoppingCart createCart(String firstName){

        Customer customer = createCustomer();
        customer.setFirstName(firstName);
        return new ShoppingCart(customer);
    }

    protected abstract Customer createCustomer();
}
