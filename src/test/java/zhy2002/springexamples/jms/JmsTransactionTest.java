package zhy2002.springexamples.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

/**
 * Test Jms Transactions.
 */
@ContextConfiguration(classes = {JmsTransactionTestConfig.class})
public class JmsTransactionTest extends AbstractTestNGSpringContextTests {

    static {
        MqStarterBean.getSingleton();
    }

    @Autowired
    private JmsTransactionalService jmsTransactionalService;

    @Test
    public void canRollbackJmsTransaction() {
        String messageSent = "Test message1";
        try {
            jmsTransactionalService.sendAndThrow(messageSent);
        } catch (Exception ex) {
            assertThat(ex.getMessage(), equalTo(messageSent));
        }

        Object message = jmsTransactionalService.receive();
        assertThat(message, nullValue());
    }

    @Test
    public void canCommitJmsTransaction() {
        String messageSent = "Test message2";
        jmsTransactionalService.send(messageSent);
        Object message = jmsTransactionalService.receive();
        assertThat(message, equalTo(messageSent));
    }


}
