package zhy2002.springexamples.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.ThrowingConsumer;

import javax.jms.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class JmsTest {

    static {
        MqStarterBean.getSingleton();
    }

    public static final String TEST_BROKER_NAME = "testBroker";
    private static final String TEST_QUEUE_NAME = "testQueue";

    private void simpleSessionTemplate(ThrowingConsumer<Session> jmsTask) throws Exception{
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://" + TEST_BROKER_NAME);

        Connection connection = null;
        Session session = null;

        try{
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            jmsTask.accept(session);

        } finally {
            if(session != null){
                session.close();
                connection.close();
            }
            if(connection != null){
                connection.close();
            }
        }
    }

    private void produceTextMessage(String text) throws Exception{
        simpleSessionTemplate(session -> {
            Destination destination = session.createQueue(TEST_QUEUE_NAME);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = session.createTextMessage(text);
            producer.send(message);
        });
    }

    private Message getMessage() throws Exception {
        final Message[] result = new Message[1];

        simpleSessionTemplate(session -> {
            Destination destination = session.createQueue(TEST_QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(destination);
            result[0] = consumer.receive(1000);
        });

        return result[0];
    }

    @Test
    public void canProduceAndConsumeMessage() throws Exception {

        String testText = "Hello test message 123";
        produceTextMessage(testText);

        Message message = getMessage();
        assertThat(message, instanceOf(TextMessage.class));

        TextMessage textMessage = (TextMessage) message;
        assertThat(textMessage.getText(), equalTo(testText));

    }

    @Test
    public void test(){

        printPermutation("", "cat");
    }

    private void printPermutation(String prefix, String rest) {
        if(rest.length() == 0){
            System.out.println(prefix);
        }

        for(int i=0; i<rest.length(); i++){
            printPermutation(prefix + rest.charAt(i), rest.substring(0, i) + rest.substring(i+1));
        }
    }
}
