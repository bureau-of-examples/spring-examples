package zhy2002.springexamples.testing;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import zhy2002.springexamples.domain.Customer;

@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class CustomerServiceImplTest extends AbstractTestNGSpringContextTests {


    @Test
    public void test(){
        Customer customer = new Customer();


    }
}
