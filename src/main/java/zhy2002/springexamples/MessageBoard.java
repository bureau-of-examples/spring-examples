package zhy2002.springexamples;
import java.util.List;

public class MessageBoard {

    private List<MessagePrinter> printers;

    public MessageBoard(List<MessagePrinter> printers){
        this.printers = printers;
    }

    public void print(){

        System.out.println("======Message Board Begin======");
        printers.forEach(MessagePrinter::print);
        System.out.println("======Message Board End======");
    }
}
