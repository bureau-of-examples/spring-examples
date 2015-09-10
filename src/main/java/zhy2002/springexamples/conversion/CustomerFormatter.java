package zhy2002.springexamples.conversion;

import org.springframework.format.Formatter;
import zhy2002.springexamples.domain.Customer;

import java.text.ParseException;
import java.util.Locale;

/**
 * Parse or format a Customer object.
 */
public class CustomerFormatter implements Formatter<Customer> {

    @Override
    public Customer parse(String text, Locale locale) throws ParseException {

        if(!text.startsWith("[") || !text.endsWith("]"))
            throw new IllegalArgumentException();

        text = text.substring(1, text.length() - 1);
        int index = text.indexOf(" - ");
        if(index < 0)
            throw new IllegalArgumentException();

        Customer customer = new Customer();
        String part = text.substring(0, index);
        if(part.length() > 0){
          customer.setId(new Long(part));
        }

        text = text.substring(index + 3);
        index = text.indexOf(' ');
        if(index < 0)
            index = 0;
        customer.setFirstName(text.substring(0, index));
        customer.setLastName(text.substring(index + 1).trim());
        return customer;
    }

    @Override
    public String print(Customer object, Locale locale) {

        return String.format("[%d - %s %s]", object.getId(), object.getFirstName(), object.getLastName());
    }
}
