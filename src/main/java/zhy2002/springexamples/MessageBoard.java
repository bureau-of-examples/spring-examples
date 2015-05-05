package zhy2002.springexamples;

import java.util.List;

public abstract class MessageBoard {

    private String boardName;
    private List<MessagePrinter> printers;

    public MessageBoard(List<MessagePrinter> printers) {
        this.printers = printers;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void print() {

        System.out.println(now().getTime());
        System.out.println("======Message Board " + getBoardName() + " Begin======");
        printers.forEach(MessagePrinter::print);
        System.out.println("======Message Board " + getBoardName() + " End======");
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {

        }

        System.out.println(now().getTime());
        System.out.println(this.getClass().toString());
    }

    public abstract TimeBean now();
}
