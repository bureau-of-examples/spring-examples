package zhy2002.springexamples.ioccontainer.xml;

import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCart;

/**
 * Test Autowire behaviour.
 */
public class AutowireTestObject {

    private Customer customer;
    private Product product;
    private ShoppingCart shoppingCart;

    public AutowireTestObject(){

    }

    public AutowireTestObject(Product product){
        this.product = product;
    }

    public AutowireTestObject(Customer customer){
        this.customer = customer;
    }

    public AutowireTestObject(ShoppingCart cart, Customer customer){
        this.shoppingCart =cart;
        this.customer =customer;
    }

    public AutowireTestObject(ShoppingCart cart, Product product){
        this.shoppingCart = cart;
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
