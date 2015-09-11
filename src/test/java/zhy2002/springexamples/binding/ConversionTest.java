package zhy2002.springexamples.binding;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.DefaultConversionSpringConfig;
import zhy2002.springexamples.common.EmptyStringConfig;
import zhy2002.springexamples.common.PropertyTestObject;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.ShoppingCartItem;
import zhy2002.springexamples.dto.CustomerTransactionDetails;
import zhy2002.springexamples.dto.OrderDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test Spring type conversion system.
 */
public class ConversionTest {

    @Test(expectedExceptions = NoSuchBeanDefinitionException.class)
    public void defaultConversionIsNotDefined(){

        //arrange
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(EmptyStringConfig.class);

        //action
        ConversionService conversionService = applicationContext.getBean(ConversionService.class);

        //assertion
        assertThat(conversionService, nullValue());

    }

    @Test()
    public void canConvertWithConversionService(){

        //arrange
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DefaultConversionSpringConfig.class);
        Product product = new Product();
        product.setName("Dell XPS13");

        //action
        ConversionService conversionService = applicationContext.getBean(ConversionService.class);
        ShoppingCartItem cartItem = conversionService.convert(product, ShoppingCartItem.class);

        //assertion
        assertThat(cartItem, notNullValue());
        assertThat(cartItem.getProduct(), sameInstance(product));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void canConvertArrayToListWithConversionService(){

        //arrange
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DefaultConversionSpringConfig.class);
        Product[] source = {new Product("Dell XPS13"), new Product("Lenovo Yoga Pro3")};

        //action
        ConversionService conversionService = applicationContext.getBean(ConversionService.class);
        List<ShoppingCartItem> items = (List<ShoppingCartItem>)conversionService.convert(
                source,
                TypeDescriptor.array(TypeDescriptor.valueOf(Product.class)),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(ShoppingCartItem.class)));

        //assertion
        assertThat(items, notNullValue());
        assertThat(items, hasSize(2));
    }

    @Test
    public void springContainerUseConversionServiceToConvertBean(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/productToCartItemConversion.xml");

        //action
        Product product = applicationContext.getBean(Product.class);
        ShoppingCart cart = applicationContext.getBean(ShoppingCart.class); //has to be lazy loaded.

        //assertion
        assertThat(cart, notNullValue());
        assertThat(cart.getItems(), hasSize(1));
        assertThat(cart.getItems().get(0).getProduct(), sameInstance(product));

    }

    /**
     * conversion service is guaranteed to be initialised first.
     * See org.springframework.context.support.AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
     */
    @Test
    public void springContainerUseConversionServiceFactoryToConvertBean(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/productToCartItemWithConversionServiceFactory.xml");

        //action
        Product product = applicationContext.getBean(Product.class);
        ShoppingCart cart = applicationContext.getBean(ShoppingCart.class); //not lazy loaded.

        //assertion
        assertThat(cart, notNullValue());
        assertThat(cart.getItems(), hasSize(1));
        assertThat(cart.getItems().get(0).getProduct(), sameInstance(product));
    }

    @Test
    public void editorTakesPrecedenceWhenConvertingFromString(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/formatterWithConversionServiceFactory.xml");

        //action
        ShoppingCart cart = applicationContext.getBean(ShoppingCart.class);

        //assertion
        assertThat(cart, notNullValue());
        assertThat(cart.getCustomer(), notNullValue());
        assertThat(cart.getCustomer().getId(), equalTo(1L));
        assertThat(cart.getCustomer().getFirstName(), equalTo("Stan"));
        assertThat(cart.getCustomer().getLastName(), equalTo("Marsh"));
    }

    @Test
    public void formatterTakesPrecedenceWehnConvertingToString(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/formatterWithConversionServiceFactory.xml");

        //action
        OrderDetails orderDetails = applicationContext.getBean(OrderDetails.class);

        //assertion
        assertThat(orderDetails.getCustomer(), equalTo("[99 - Eric Cartman]"));
    }

    @Test
    public void editorIsUsedWhenCoerceBeanPropertyValue(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/annotationFormatter.xml");

        //action
        CustomerTransactionDetails customerTransactionDetails = applicationContext.getBean(CustomerTransactionDetails.class);

        //assertion
        assertThat(customerTransactionDetails.getCustomer(), notNullValue());
        assertThat(customerTransactionDetails.getCustomer().getId(), nullValue());
        assertThat(customerTransactionDetails.getCustomer().getFirstName(), equalTo("32"));
        assertThat(customerTransactionDetails.getCustomer().getLastName(), equalTo("- Elvis Presley"));
    }

    @Test
    public void canChangeDefaultDateParsingWithCustomEditorConfigurer() throws ParseException{

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/globalDateFormat.xml");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //action
        OrderDetails orderDetails = applicationContext.getBean(OrderDetails.class);

        //assertion
        assertThat(orderDetails.getOrderDate(), equalTo(dateFormat.parse("2013-12-05")));

    }

    @Test
    public void canConvertProperties(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/conversion.xml");

        //action
        PropertyTestObject testObject = applicationContext.getBean(PropertyTestObject.class);

        //assertion
        assertThat(testObject.getConfigProperties().getProperty("prop1"), equalTo("test1"));
        assertThat(testObject.getConfigProperties().getProperty("prop2.text"), equalTo("test2"));
    }

    @Test(expectedExceptions = BeanCreationException.class)
    public void idRefIsChecked(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/idref.xml");

        //action


        //assertion
    }

    @Test()
    public void idRefIsFulfilled(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("conversiontest/idref.xml", "conversiontest/nonExistentBean.xml");

        //action
        PropertyTestObject testObject = applicationContext.getBean(PropertyTestObject.class);

        //assertion
        assertThat(testObject.getBeanId(), equalTo("nonExistentBean"));
    }
}
