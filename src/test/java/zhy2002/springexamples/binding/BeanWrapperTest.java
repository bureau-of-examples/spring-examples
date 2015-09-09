package zhy2002.springexamples.binding;

import com.sun.beans.editors.ColorEditor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.PropertyTestObject;
import zhy2002.springexamples.domain.*;

import java.awt.*;
import java.beans.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test BeanWrapper behaviour.
 */
public class BeanWrapperTest {

    private BeanWrapper createBeanWrapper(Object target) {
        return new BeanWrapperImpl(target);
    }

    private BeanWrapper createBeanWrapper(Class<?> clazz) {
        return new BeanWrapperImpl(clazz);
    }

    @Test
    public void canSetProperties() {

        //arrange
        ShoppingCart cart = new ShoppingCart();
        BeanWrapper wrapper = createBeanWrapper(cart);

        //action
        Date now = new Date();
        wrapper.setPropertyValue("dateCreated", now);
        wrapper.setPropertyValue("customer", new Customer()); //nested object cannot be null if we want to set nested object's properties
        wrapper.setPropertyValue("customer.firstName", "Alex"); //nested property
        wrapper.setPropertyValue("customer.lastName", "Murphy");
        wrapper.setPropertyValue("customer.vip", true); //auto boxing
        wrapper.setPropertyValue("items[0]", new ShoppingCartItem());
        wrapper.setPropertyValue("items[0].product", new Product());
        wrapper.setPropertyValue("items[0].product.name", "Lego Star Wars");
        wrapper.setPropertyValue("items[0].quantity", new BigDecimal("5"));

        //assertion
        assertThat(cart.getDateCreated(), equalTo(now));
        assertThat(cart.getCustomer().getFirstName(), equalTo("Alex"));
        assertThat(cart.getCustomer().getLastName(), equalTo("Murphy"));
        assertThat(cart.getCustomer().getVip(), equalTo(true));
        assertThat(cart.getItems().size(), equalTo(1));
        assertThat(cart.getItems().get(0).getProduct().getName(), equalTo("Lego Star Wars"));
        assertThat(cart.getItems().get(0).getQuantity(), equalTo(new BigDecimal("5")));

    }

    @Test
    public void canGetProperties() {

        //arrange
        ShoppingCart cart = new ShoppingCart();
        Date now = new Date();
        cart.setDateCreated(now);
        cart.setCustomer(new Customer()); //nested object cannot be null if we want to get nested object's properties
        cart.getCustomer().setFirstName("John");
        cart.getCustomer().setLastName("Rambo");
        ShoppingCartItem item1 = new ShoppingCartItem();
        item1.setProduct(new Product());
        item1.getProduct().setName("Monopoly");
        item1.setQuantity(new BigDecimal("12.99"));
        cart.getItems().add(item1);
        BeanWrapper wrapper = createBeanWrapper(cart);

        //action

        //assertion
        assertThat(wrapper.getPropertyValue("dateCreated"), equalTo(now));
        assertThat(wrapper.getPropertyValue("customer.id"), nullValue());
        assertThat(wrapper.getPropertyValue("customer.firstName"), equalTo("John"));
        assertThat(wrapper.getPropertyValue("customer.lastName"), equalTo("Rambo"));
        assertThat((Collection<?>) wrapper.getPropertyValue("items"), hasSize(1));
        assertThat(wrapper.getPropertyValue("items[0].product.name"), equalTo("Monopoly"));
        assertThat(wrapper.getPropertyValue("items[0].quantity"), equalTo(new BigDecimal("12.99")));
        //wrapper.getPropertyValue("items[1]....") will cause 'Index of out of bounds' exception
        assertThat(wrapper.getWrappedInstance(), sameInstance(cart));

        //Accessing map is also possible: account[COMPANY_NAME] Indicates the value of the map entry indexed by the key COMPANY_NAME of the Map property account
    }

    @Test
    public void canConvertUsingDefaultPropertyEditors() throws ParseException {

        //arrange
        BeanWrapper wrapper = createBeanWrapper(PropertyTestObject.class);
        PropertyTestObject testObject = (PropertyTestObject) wrapper.getWrappedInstance();

        //action
        wrapper.setPropertyValue("clazz", "java.math.BigDecimal");
        wrapper.setPropertyValue("real", "on");
        wrapper.setPropertyValue("baseFile", "text1.txt");
        wrapper.setPropertyValue("uiLocale", "en AU");
        wrapper.setPropertyValue("website", "http://www.google.com.au");
        //wrapper.setPropertyValue("backgroundColor", "255,255,255"); com.sun.beans.editors are not registered in standalone BeanWrapper.

        //assertion
        assertThat(testObject.getClazz(), sameInstance(BigDecimal.class));
        assertThat(testObject.getReal(), equalTo(Boolean.TRUE));
        assertThat(testObject.getBaseFile().getName(), equalTo("text1.txt"));
        assertThat(testObject.getUiLocale(), equalTo(new Locale("en", "AU")));
        assertThat(testObject.getWebsite().toString(), equalTo("http://www.google.com.au"));
    }

    @Test
    public void propertyEditorManagerContainsSunBeansEditorsByDefault() {
        //arrange

        //action
        String[] paths = PropertyEditorManager.getEditorSearchPath();

        //assertion
        assertThat(paths.length, equalTo(1));
        assertThat(paths[0], equalTo("sun.beans.editors")); //however the real package name is com.sun.beans.editors
    }

    @Test
    public void canUseDefaultJavaBeansPropertyEditors() {

        //arrange

        //action
        PropertyEditor colorPropertyEditor = PropertyEditorManager.findEditor(Color.class);
        PropertyEditor colorPropertyEditor2 = PropertyEditorManager.findEditor(Color.class);

        //assertion
        assertThat(colorPropertyEditor, not(nullValue()));
        assertThat(colorPropertyEditor, instanceOf(ColorEditor.class));
        assertThat(colorPropertyEditor, not(sameInstance(colorPropertyEditor2)));

        //action
        colorPropertyEditor.setAsText("255,255,255");
        Color color = (Color) colorPropertyEditor.getValue();

        //assertion
        assertThat(color, equalTo(new Color(255, 255, 255)));
    }

    @Test
    public void canLoadConventionalPropertyEditor() {

        //arrange

        //action
        PropertyEditor propertyEditor = PropertyEditorManager.findEditor(Customer.class);

        //assertion
        assertThat(propertyEditor, notNullValue());
        assertThat(propertyEditor, instanceOf(CustomerEditor.class));
    }

    @Test
    public void canLoadConventionalBeanInfo() throws IntrospectionException {

        //arrange

        //action
        BeanInfo definedBeanInfo = Introspector.getBeanInfo(SpecialShoppingCart.class, Introspector.USE_ALL_BEANINFO);
        BeanInfo defaultBeanInfo = Introspector.getBeanInfo(SpecialShoppingCart.class, Introspector.IGNORE_IMMEDIATE_BEANINFO);


        //assertion
        assertThat(defaultBeanInfo, notNullValue());
        assertThat(defaultBeanInfo, notNullValue());
        assertThat(defaultBeanInfo, not(sameInstance(definedBeanInfo)));
        assertThat(definedBeanInfo.getPropertyDescriptors().length, equalTo(1));
        assertThat(defaultBeanInfo.getPropertyDescriptors().length, greaterThan(1));
        assertThat(definedBeanInfo.getPropertyDescriptors()[0].createPropertyEditor(new Customer()), instanceOf(ShoppingCartCustomerEditor.class));
    }

    @Test
    public void customEditorCanCreateInstance() {
        //arrange
        CustomerEditor customerEditor = new CustomerEditor();

        //action
        customerEditor.setAsText("Issac Newton");
        Customer customer = (Customer) customerEditor.getValue();

        //assertion
        assertThat(customer, notNullValue());
        assertThat(customer.getFirstName(), equalTo("Issac"));
        assertThat(customer.getLastName(), equalTo("Newton"));
    }

    @Test()
    public void beanWrapperCanUseConventionalEditor() {

        //arrange
        BeanWrapper wrapper = createBeanWrapper(new ShoppingCart());

        //action
        wrapper.setPropertyValue("customer", "Stephen Hawking");
        Customer customer = (Customer) wrapper.getPropertyValue("customer");

        //assertion
        assertThat(customer, notNullValue());
        assertThat(customer.getFirstName(), equalTo("Stephen"));
        assertThat(customer.getLastName(), equalTo("Hawking"));

    }

    @Test()
    public void beanWrapperCanRegisterCustomEditor() {

        //arrange
        BeanWrapper wrapper = createBeanWrapper(new ShoppingCart());
        wrapper.registerCustomEditor(Customer.class, new ShoppingCartCustomerEditor());

        //action
        wrapper.setPropertyValue("customer", "32,Stephen,Hawking");
        Customer customer = (Customer) wrapper.getPropertyValue("customer");

        //assertion
        assertThat(customer, notNullValue());
        assertThat(customer.getId(), equalTo(32L));
        assertThat(customer.getFirstName(), equalTo("Stephen"));
        assertThat(customer.getLastName(), equalTo("Hawking"));
    }

    @Test
    public void beanInfoDefinedPropertyEditorDoesNotWork() {

        //arrange
        BeanWrapper wrapper = createBeanWrapper(SpecialShoppingCart.class);

        //action
        wrapper.setPropertyValue("customer", "32,Harry,Porter");
        ShoppingCart cart = (ShoppingCart) wrapper.getWrappedInstance();

        //assertion
        assertThat(cart.getCustomer(), notNullValue());
        assertThat(cart.getCustomer().getId(), nullValue());
        assertThat(cart.getCustomer().getFirstName(), nullValue());
        assertThat(cart.getCustomer().getLastName(), equalTo("32,Harry,Porter"));

    }

    //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(simpleDateFormat.format(new Date()));
//
//        Date date = simpleDateFormat.parse("2011-02-14");

}
