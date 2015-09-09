package zhy2002.springexamples.binding;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.DefaultConversionSpringConfig;
import zhy2002.springexamples.common.EmptyStringConfig;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCartItem;

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
}
