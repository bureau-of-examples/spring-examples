package zhy2002.springexamples.domain;

import java.beans.*;

/**
 * BeanInfo class for ShoppingCart class.
 */
public class SpecialShoppingCartBeanInfo extends SimpleBeanInfo {

    private static final PropertyDescriptor[] PROPERTY_DESCRIPTORS;

    static {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor("customer", ShoppingCart.class){
                @Override
                public PropertyEditor createPropertyEditor(Object bean) {
                    return new ShoppingCartCustomerEditor((Customer)bean);
                }
            };

            PROPERTY_DESCRIPTORS = new PropertyDescriptor[]{propertyDescriptor};
        }catch (IntrospectionException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PROPERTY_DESCRIPTORS;
    }
}
