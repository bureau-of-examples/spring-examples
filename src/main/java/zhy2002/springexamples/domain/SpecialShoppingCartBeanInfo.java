package zhy2002.springexamples.domain;

import java.beans.*;
import java.util.Date;

/**
 * BeanInfo class for ShoppingCart class.
 */
public class SpecialShoppingCartBeanInfo extends SimpleBeanInfo {

    private static final PropertyDescriptor[] PROPERTY_DESCRIPTORS;

    static {
        try {
            PropertyDescriptor customerDescriptor = new PropertyDescriptor("customer", SpecialShoppingCart.class){
                @Override
                public PropertyEditor createPropertyEditor(Object bean) {
                    return new ShoppingCartCustomerEditor((Customer)bean);
                }
            };

            PropertyDescriptor dateCreatedDescriptor = new PropertyDescriptor("dateCreated", SpecialShoppingCart.class){

                @Override
                public PropertyEditor createPropertyEditor(Object bean) {
                    return new DefaultDateEditor((Date)bean);
                }
            };

            PROPERTY_DESCRIPTORS = new PropertyDescriptor[]{dateCreatedDescriptor, customerDescriptor};
        }catch (IntrospectionException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PROPERTY_DESCRIPTORS;
    }
}
