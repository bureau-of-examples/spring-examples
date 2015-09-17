package zhy2002.springexamples.domain;

/**
 * Created by JOHNZ on 17/09/2015.
 */
public class Color {

    private String value;

    public Color(){

    }

    public Color(String color){
        value = color;
    }

    @Override
    public String toString() {
        return value;
    }
}
