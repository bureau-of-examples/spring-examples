package zhy2002.springexamples.console;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zhy2002.springexamples.MessageBoard;
import zhy2002.springexamples.MessageHolder;
import zhy2002.springexamples.MessagePrinter;

public class Program {

    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("console_spring.xml");//ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});
        MessageHolder helloMessage = (MessageHolder)applicationContext.getBean("helloMessage");
        System.out.println(helloMessage.getMessage());
        MessagePrinter helloPrinter = (MessagePrinter)applicationContext.getBean("helloPrinter");
        helloPrinter.print();

        MessageBoard messageBoard = applicationContext.getBean("messageBoard", MessageBoard.class);
        messageBoard.print();

    }
}
