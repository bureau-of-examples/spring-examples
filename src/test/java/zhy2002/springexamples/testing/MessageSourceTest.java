package zhy2002.springexamples.testing;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.DelegatingMessageSource;
import org.testng.annotations.Test;
import zhy2002.springexamples.EmptyStringConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 *
 */
public class MessageSourceTest {


    @Test
    public void applicationContextDefinesDelegatingMessageSource(){

        //arrange
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EmptyStringConfig.class);

        //action
        MessageSource messageSource = context.getBean(MessageSource.class);

        //assertion
        assertThat(messageSource, not(nullValue()));
        assertThat(messageSource instanceof DelegatingMessageSource, equalTo(true));
    }
}
