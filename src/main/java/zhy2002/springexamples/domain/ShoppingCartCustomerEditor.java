package zhy2002.springexamples.domain;

import java.beans.PropertyEditorSupport;

/**
 * The default property editor for customer class.
 */
public class ShoppingCartCustomerEditor extends PropertyEditorSupport{

    public ShoppingCartCustomerEditor(){
        this(new Customer());
    }

    public ShoppingCartCustomerEditor(Customer customer){
        super(customer);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String[] values = text.split(",");
        Customer customer = (Customer)getSource();
        if(values.length > 0){
           customer.setId(new Long(values[0]));
        }
        if(values.length > 1){
            customer.setFirstName(values[1]);
        }
        if(values.length > 2){
            customer.setLastName(values[2]);
        }
        if(values.length > 3){
            customer.setVip(new Boolean(values[3]));
        }

        setValue(customer);
    }
}
