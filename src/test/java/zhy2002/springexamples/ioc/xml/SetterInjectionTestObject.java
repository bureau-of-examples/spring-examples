package zhy2002.springexamples.ioc.xml;

import zhy2002.springexamples.domain.Customer;

/**
 * Test setter injection behaviour.
 */
public class SetterInjectionTestObject {

    private Customer customer;
    private SetterInjectionTestObject friend;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SetterInjectionTestObject getFriend() {
        return friend;
    }

    public void setFriend(SetterInjectionTestObject friend) {
        this.friend = friend;
    }
}
