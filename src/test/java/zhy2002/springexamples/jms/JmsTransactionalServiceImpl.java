package zhy2002.springexamples.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;


public class JmsTransactionalServiceImpl implements JmsTransactionalService {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Resource(name = "myDestination")
    private Destination destination;

    @Transactional(transactionManager = "jmsTransactionManager")
    @Override
    public void sendAndThrow(String message) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.convertAndSend(destination, message);
        throw new RuntimeException(message);
    }

    public Object receive(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(1);
        return jmsTemplate.receiveAndConvert(destination);
    }

    @Transactional
    public void send(String message){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.convertAndSend(destination, message);
    }
}
