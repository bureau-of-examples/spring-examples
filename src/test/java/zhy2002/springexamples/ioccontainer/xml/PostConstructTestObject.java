package zhy2002.springexamples.ioccontainer.xml;

import java.util.ArrayList;

/**
 * An object designed to test Spring container post construct behaviour.
 */
public class PostConstructTestObject {

    private ArrayList<String> output;
    private String name;
    private PostConstructTestObject other;

    public  PostConstructTestObject(ArrayList<String> output){
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostConstructTestObject getOther() {
        return other;
    }

    public void setOther(PostConstructTestObject other) {
        this.other = other;
    }

    public void init(){
        this.name += "(post constructed)";
        output.add("other: " + other.getName());
    }


}
