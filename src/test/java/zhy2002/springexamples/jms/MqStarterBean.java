package zhy2002.springexamples.jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.Ordered;

import javax.annotation.PreDestroy;

/**
 * Start a mq broker service.
 */
public class MqStarterBean {

    private static final MqStarterBean singleton;

    static {
        singleton = new MqStarterBean(33333);
    }

    public static MqStarterBean getSingleton(){
        return singleton;
    }

    private BrokerService brokerService;

    private MqStarterBean(int port) {

        try {
            brokerService = new BrokerService();

            // configure the broker
            brokerService.addConnector("tcp://localhost:" + port);
            brokerService.setUseJmx(false);
            brokerService.setBrokerName(JmsTest.TEST_BROKER_NAME);
            brokerService.start();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }

    @PreDestroy
    public void close(){
        try {
            brokerService.stop();
        }catch (Exception ex){

        }
    }

}
