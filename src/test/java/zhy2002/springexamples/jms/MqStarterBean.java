package zhy2002.springexamples.jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.Ordered;

import javax.annotation.PreDestroy;

/**
 * Start a mq broker service.
 */
public class MqStarterBean implements Ordered {

    private BrokerService brokerService;

    public MqStarterBean() {

        try {
            brokerService = new BrokerService();

            // configure the broker
            brokerService.addConnector("tcp://localhost:30333");
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
