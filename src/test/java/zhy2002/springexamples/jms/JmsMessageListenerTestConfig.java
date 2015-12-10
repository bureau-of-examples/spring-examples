package zhy2002.springexamples.jms;


import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;

@Configuration
public class JmsMessageListenerTestConfig extends JmsTransactionTestConfig {

    @Bean(name = "listenerDestination")
    public Destination getListenerDestination(){
        return new ActiveMQQueue("listenerQueue");
    }

    @Bean
    public MyMessageListener getMyMessageListener(){
        return new MyMessageListener();
    }

    @Bean(name = "myService")
    public JmsTransactionalService getMyJmsTransactionService(){
        return new JmsTransactionalServiceImpl(getListenerDestination());
    }

    @Bean
    public DefaultMessageListenerContainer getDefaultMessageListenerContainer() throws Exception {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(getConnectionFactory());
        messageListenerContainer.setDestination(getListenerDestination());
        messageListenerContainer.setMessageListener(getMyMessageListener());
        messageListenerContainer.setTransactionManager(getTransactionManager());
        messageListenerContainer.setSessionTransacted(true);
        return messageListenerContainer;
    }
}
