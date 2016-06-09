package zhy2002.springexamples.ioccontainer.xml;

/**
 * A simple config object used in testing.
 */
public class MyConfig {

    private String theValue;

    public MyConfig(String value) {
        theValue = value;
    }

    public String getTheValue() {
        return theValue;
    }

    public void setTheValue(String theValue) {
        this.theValue = theValue;
    }
}
