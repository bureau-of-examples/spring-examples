package zhy2002.springexamples.jms;

/**
 * Created by JOHNZ on 9/12/2015.
 */
public class JmsRemoteServiceImpl implements JmsRemoteService {

    public String call(String arg){
        return "Message from jms remote service: arg is " + arg;
    }
}
