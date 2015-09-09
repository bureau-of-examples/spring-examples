package zhy2002.springexamples.domain;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/**
 * Register property editors for domain types.
 */
public class DomainPropertyEditorRegistrar implements PropertyEditorRegistrar {

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Customer.class, new ShoppingCartCustomerEditor());
    }
}
