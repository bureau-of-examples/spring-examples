package zhy2002.springexamples.dto;


import java.util.Date;

/**
 * View model order details.
 */
public class OrderDetails {

    private String customer;
    private Date orderDate;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
