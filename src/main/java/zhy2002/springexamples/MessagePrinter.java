package zhy2002.springexamples;


public class MessagePrinter {

    private MessageHolder messageHolder;
    private MessageFormatter messageFormatter;

    public MessagePrinter(MessageHolder messageHolder, MessageFormatter formatter){
        this.messageHolder = messageHolder;
        this.messageFormatter = formatter;
    }

    public void print(){
        if(messageFormatter != null)
            System.out.println(messageFormatter.format("Message Printer: " + messageHolder.getMessage()));
        else
            System.out.println("Message Printer: " + messageHolder.getMessage());
    }
}
