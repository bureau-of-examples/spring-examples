package zhy2002.springexamples.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class ShoppingCart {

    public ShoppingCart(){
       System.out.println("Creating cart...");
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
