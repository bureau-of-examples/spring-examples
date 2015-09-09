package zhy2002.springexamples.domain;

import java.beans.PropertyEditorSupport;

/**
 * The default property editor for customer class.
 */
public class CustomerEditor extends PropertyEditorSupport {

    public CustomerEditor() {
        this(new Customer());
    }

    public CustomerEditor(Customer customer) {
        super(customer);
        setValue(getSource());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        Customer customer = (Customer) getSource();
        int index = text.indexOf(' ');
        if (index > 0) {
            customer.setFirstName(text.substring(0, index));
        }

        customer.setLastName(text.substring(index + 1));
        setValue(customer);

    }
}
