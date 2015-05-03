package zhy2002.springexamples;


public class MessagePrinter {

    private MessageHolder messageHolder;

    public MessagePrinter(MessageHolder messageHolder){
        this.messageHolder = messageHolder;
    }

    public void print(){
        System.out.println("Message Printer: " + messageHolder.getMessage());
    }
}
