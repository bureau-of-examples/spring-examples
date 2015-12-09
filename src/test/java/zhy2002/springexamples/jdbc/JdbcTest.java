package zhy2002.springexamples.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = {JdbcConfig.class})
public class JdbcTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerService customerService;

    @Test
    public void canQueryForAScalarValue() {

        assertThat(customerDAO, notNullValue());

        int customerCount = customerDAO.getCustomerCount();

        assertThat(customerCount, greaterThan(0));

    }

    @Test
    public void sqlExceptionIsWrappedBySpring() {
        try {
            customerDAO.queryNonExistentTable();
        } catch (DataAccessException ex) {
            //do nothing
        }
    }

    private static final String NEW_UNIQUE_NAME = "eeee";
    @Test
    public void canRollbackTransaction(){

        try{
            customerService.updateAndException(NEW_UNIQUE_NAME);
        }catch (RuntimeException ex){
            //expected
        }

        int customerId = customerDAO.getFirstCustomerId();
        String name = customerDAO.getCustomerName(customerId);
        assertThat(name, not(equalTo(NEW_UNIQUE_NAME)));

    }

}
