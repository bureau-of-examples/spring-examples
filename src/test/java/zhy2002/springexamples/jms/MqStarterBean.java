package zhy2002.springexamples.jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.Ordered;

import javax.annotation.PreDestroy;

/**
 * Start a mq broker service.
 */
public class MqStarterBean implements Ordered {

    private BrokerService brokerService;

    public MqStarterBean(){
        this(30333);
    }

    public MqStarterBean(int port) {

        try {
            brokerService = new BrokerService();

            // configure the broker
            brokerService.addConnector("tcp://localhost:" + port);
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

    @Override
    public int getOrder() {
        return 0;
    }
}
