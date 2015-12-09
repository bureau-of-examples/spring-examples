package zhy2002.springexamples.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ContextConfiguration(classes = {JmsTemplateTestConfig.class})
public class JmsTemplateTest extends AbstractTestNGSpringContextTests {

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

}
