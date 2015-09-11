package zhy2002.springexamples.domain;

import zhy2002.springexamples.validation.ShoppingCartConstraint;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  A shopping cart.
 */
@ShoppingCartConstraint
public class ShoppingCart {

    public ShoppingCart(){
       System.out.println("Creating cart...");
    }

    @ConstructorProperties({"customer"})
    public ShoppingCart(Customer customer){
        this.customer = customer;
    }

    private Date dateCreated;
    private Customer customer;
    private List<ShoppingCartItem> items = new ArrayList<>();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
