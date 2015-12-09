package zhy2002.springexamples.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;


@Configuration
public class JmsTemplateTestConfig {

    @Bean
    public ConnectionFactory getConnectionFactory() throws Exception {
        return new ActiveMQConnectionFactory("tcp://localhost:33333");
    }

    @Bean
    public Destination getDestination() {
        return new ActiveMQQueue("cbis-request");
    }

//    @Bean
//    public MqStarterBean getMqStarterBean(){
//       return new MqStarterBean(30335);
//    }
}
