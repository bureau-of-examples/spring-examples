package zhy2002.springexamples.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHNZ on 10/12/2015.
 */
public class MyMessageListener implements MessageListener{

    public static final List<String> MESSAGES = new ArrayList<>();


    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                MESSAGES.add(text);
                System.out.println(text);

                if("listener throws".equals(text) && MESSAGES.size() == 1) {
                    throw new RuntimeException("Listener throws exception");
                }

            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }
}
