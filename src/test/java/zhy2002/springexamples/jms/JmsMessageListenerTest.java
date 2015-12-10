package zhy2002.springexamples.jms;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;

/**
 * Test using transaction with MessageListenerContainer.
 */
@ContextConfiguration(classes = {JmsMessageListenerTestConfig.class})
public class JmsMessageListenerTest extends AbstractTestNGSpringContextTests {

    static {
        MqStarterBean.getSingleton();
    }

    @Resource(name = "myService")
    private JmsTransactionalService jmsTransactionalService;

    @Test
    public void canReceiveMessage() throws Exception{

        synchronized (JmsMessageListenerTest.class){
            jmsTransactionalService.send("Test message xxx");

            Thread.sleep(1000);

            assertThat(MyMessageListener.MESSAGES, hasSize(1));

            MyMessageListener.MESSAGES.clear();
        }
    }

    @Test
    public void canRetryOnFailure() throws Exception{
        synchronized (JmsMessageListenerTest.class){
            jmsTransactionalService.send("listener throws");

            Thread.sleep(1000);

            assertThat(MyMessageListener.MESSAGES, hasSize(2));
            MyMessageListener.MESSAGES.clear();
            Object object = jmsTransactionalService.receive();
            assertThat(object, nullValue());
        }
    }



}
