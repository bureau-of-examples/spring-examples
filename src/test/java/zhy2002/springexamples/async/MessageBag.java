package zhy2002.springexamples.async;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A  bean used to receive messages.
 */
public class MessageBag {

    private List<String> items;

    public MessageBag(){
        items = Collections.synchronizedList(new ArrayList<>());
    }

    public List<String> getItems() {
        return items;
    }
}
