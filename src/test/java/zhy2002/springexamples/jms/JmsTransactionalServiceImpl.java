package zhy2002.springexamples.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;


public class JmsTransactionalServiceImpl implements JmsTransactionalService {


    public JmsTransactionalServiceImpl(Destination destination){
        this.destination = destination;
    }

    @Autowired
    private ConnectionFactory connectionFactory;

    private Destination destination;

    @Transactional(transactionManager = "jmsTransactionManager")
    @Override
    public void sendAndThrow(String message) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.convertAndSend(destination, message);
        throw new RuntimeException(message);
    }

    @Transactional(transactionManager = "jmsTransactionManager")
    public Object receive(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(100);
        return jmsTemplate.receiveAndConvert(destination);
    }

    @Transactional(transactionManager = "jmsTransactionManager")
    public void send(String message){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.convertAndSend(destination, message);
    }
}
