package zhy2002.springexamples.conversion;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import zhy2002.springexamples.domain.Customer;

import java.util.HashSet;
import java.util.Set;

/**
 * Get the formatter for a domain object.
 */
public class DomainAnnotationFormatterFactory implements AnnotationFormatterFactory<DomainObjectFormat>{

    private static final Set<Class<?>> SUPPORTED_CLASSES = new HashSet<>();
    private CustomerFormatter customerFormatter = new CustomerFormatter();

    static {
        SUPPORTED_CLASSES.add(Customer.class);
    }


    @Override
    public Set<Class<?>> getFieldTypes() {
        return SUPPORTED_CLASSES;
    }

    @Override
    public Printer<?> getPrinter(DomainObjectFormat annotation, Class<?> fieldType) {
        if(SUPPORTED_CLASSES.contains(fieldType))
            return customerFormatter;

        return null;
    }

    @Override
    public Parser<?> getParser(DomainObjectFormat annotation, Class<?> fieldType) {
        if(SUPPORTED_CLASSES.contains(fieldType))
            return customerFormatter;

        return null;
    }
}
