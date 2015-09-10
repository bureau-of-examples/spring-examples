package zhy2002.springexamples.conversion;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Property editor for a java.util.Date class.
 */
public class DefaultDateEditor extends PropertyEditorSupport {

    public DefaultDateEditor(){
        this(null);
    }

    public DefaultDateEditor(Date date){
        setSource(date);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] parts = text.split("-");
        if (parts.length != 3)
            throw new IllegalArgumentException();

        GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]));
        Date value = calendar.getTime();
        setValue(value);
    }
}
