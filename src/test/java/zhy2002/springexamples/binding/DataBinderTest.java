package zhy2002.springexamples.binding;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.DataBinder;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.PropertyTestObject;
import zhy2002.springexamples.conversion.CustomerFormatter;
import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.CustomerEditor;
import zhy2002.springexamples.domain.ShoppingCart;

import java.math.BigDecimal;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test use of a data binder.
 */
public class DataBinderTest {

    /**
     * See also zhy2002.springexamples.binding.BeanWrapperTest#canConvertUsingDefaultPropertyEditors().
     */
    @Test
    public void dataBinderCanUseDefaultBeanWrapperPropertyEditors(){

        //arrange
        PropertyTestObject testObject = new PropertyTestObject();
        DataBinder dataBinder = new DataBinder(testObject);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("clazz", "java.math.BigDecimal");
        propertyValues.addPropertyValue("real", "on");
        propertyValues.addPropertyValue("baseFile", "text1.txt");
        propertyValues.addPropertyValue("uiLocale", "en AU");
        propertyValues.addPropertyValue("website", "http://www.google.com.au");

        //action
        dataBinder.bind(propertyValues);

        //assertion
        assertThat(testObject.getClazz(), sameInstance(BigDecimal.class));
        assertThat(testObject.getReal(), equalTo(Boolean.TRUE));
        assertThat(testObject.getBaseFile().getName(), equalTo("text1.txt"));
        assertThat(testObject.getUiLocale(), equalTo(new Locale("en", "AU")));
        assertThat(testObject.getWebsite().toString(), equalTo("http://www.google.com.au"));
    }

    @Test
    public void dataBinderUsePropertyEditorOverFormatter(){

        //arrange
        ShoppingCart cart = new ShoppingCart();
        DataBinder dataBinder = new DataBinder(cart);
        dataBinder.registerCustomEditor(Customer.class, new CustomerEditor());
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addFormatter(new CustomerFormatter());
        dataBinder.setConversionService(conversionService);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("customer", "[1 - Jon Snow]");

        //action
        dataBinder.bind(propertyValues);

        //assertion
        assertThat(cart.getCustomer(), notNullValue());
        assertThat(cart.getCustomer().getId(), nullValue());
        assertThat(cart.getCustomer().getFirstName(), equalTo("[1"));

    }

    @Test
    public void dataBinderCanUseFormatter(){

        //arrange
        ShoppingCart cart = new ShoppingCart();
        DataBinder dataBinder = new DataBinder(cart);
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addFormatter(new CustomerFormatter());
        dataBinder.setConversionService(conversionService);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("customer", "[1 - Jon Snow]");

        //action
        dataBinder.bind(propertyValues);

        //assertion
        assertThat(cart.getCustomer(), notNullValue());
        assertThat(cart.getCustomer().getId(), equalTo(1L));
        assertThat(cart.getCustomer().getFirstName(), equalTo("Jon"));
        assertThat(cart.getCustomer().getLastName(), equalTo("Snow"));

    }

}
