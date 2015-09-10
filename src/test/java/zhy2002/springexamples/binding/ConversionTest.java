package zhy2002.springexamples.binding;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.DefaultConversionSpringConfig;
import zhy2002.springexamples.common.EmptyStringConfig;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.ShoppingCartItem;

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
}
