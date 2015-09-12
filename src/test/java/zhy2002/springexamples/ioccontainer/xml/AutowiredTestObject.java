package zhy2002.springexamples.ioccontainer.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import zhy2002.springexamples.domain.Customer;

/**
 * Test class used to check autowired behaviour.
 */
@Component("testObject1")
public class AutowiredTestObject {

    @Autowired
    private Customer requiredCustomer;

    @Qualifier("optionalCustomer")
    @Autowired(required = false)
    private Customer optionalCustomer;

    public Customer getRequiredCustomer() {
        return requiredCustomer;
    }

    public void setRequiredCustomer(Customer requiredCustomer) {
        this.requiredCustomer = requiredCustomer;
    }

    public Customer getOptionalCustomer() {
        return optionalCustomer;
    }

    public void setOptionalCustomer(Customer optionalCustomer) {
        this.optionalCustomer = optionalCustomer;
    }
}
