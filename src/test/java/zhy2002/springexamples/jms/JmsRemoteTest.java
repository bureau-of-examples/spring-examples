package zhy2002.springexamples.jms;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test exposing a service via jms.
 */
@ContextConfiguration("classpath:jmsremotetest/client.xml")
public class JmsRemoteTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private JmsRemoteService jmsRemoteService;

    //need to start active mq to do this test.
    @Test
    public void testJmsRemoteAccess(){

        ClassPathXmlApplicationContext serverContext = new ClassPathXmlApplicationContext("classpath:jmsremotetest/server.xml");
        String response = jmsRemoteService.call("test 1");

        assertThat(response, equalTo("Message from jms remote service: arg is test 1"));

    }



}
