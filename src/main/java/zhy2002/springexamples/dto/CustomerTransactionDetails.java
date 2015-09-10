package zhy2002.springexamples.dto;

import zhy2002.springexamples.conversion.DomainObjectFormat;
import zhy2002.springexamples.domain.Customer;

/**
 * DTO object.
 */
public class CustomerTransactionDetails {

    @DomainObjectFormat
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
