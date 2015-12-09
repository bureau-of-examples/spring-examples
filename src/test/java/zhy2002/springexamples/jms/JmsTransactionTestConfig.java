package zhy2002.springexamples.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.Destination;

/**
 * Created by JOHNZ on 10/12/2015.
 */
@EnableTransactionManagement
@Configuration
public class JmsTransactionTestConfig extends JmsTemplateTestConfig{

    @Bean(name = "jmsTransactionManager")
    public PlatformTransactionManager getTransactionManager() throws Exception{
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager(getConnectionFactory());
        jmsTransactionManager.setDefaultTimeout(10);
        return jmsTransactionManager;
    }

    @Bean(name = "myDestination")
    public Destination getMyDestination() {
        return new ActiveMQQueue("myQueue");
    }

    @Bean
    public JmsTransactionalService getJmsTransactionalService(){
        return new JmsTransactionalServiceImpl();
    }

}
