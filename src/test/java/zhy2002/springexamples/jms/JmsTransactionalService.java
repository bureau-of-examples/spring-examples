package zhy2002.springexamples.jms;

/**
 * Created by JOHNZ on 10/12/2015.
 */
public interface JmsTransactionalService {

    void sendAndThrow(String message);

    void send(String message);

    Object receive();
}
