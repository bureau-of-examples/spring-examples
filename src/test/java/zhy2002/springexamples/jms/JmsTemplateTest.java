package zhy2002.springexamples.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * JMS acknowledge modes:
 * http://docs.oracle.com/javaee/7/api/javax/jms/Session.html?is-external=true#DUPS_OK_ACKNOWLEDGE
 */
@ContextConfiguration(classes = {JmsTemplateTestConfig.class})
public class JmsTemplateTest extends AbstractTestNGSpringContextTests {

    static {
        MqStarterBean.getSingleton();
    }

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Destination destination;

    private static final String TEXT_MESSAGE = "Hello how are you?";

    @Test
    public void canSendAndReceiveMessage(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        String message = TEXT_MESSAGE + new Date();
        jmsTemplate.convertAndSend(destination, message);

        Object result = jmsTemplate.receiveAndConvert(destination);
        assertThat(result, equalTo(message));
    }

    @Test
    public void  useAutoAcknowledgeModeByDefault(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        int mode = jmsTemplate.getSessionAcknowledgeMode();
        assertThat(mode, equalTo(Session.AUTO_ACKNOWLEDGE));

    }

}
