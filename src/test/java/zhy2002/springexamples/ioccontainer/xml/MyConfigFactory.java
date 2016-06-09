package zhy2002.springexamples.ioccontainer.xml;

/**
 * A bean which creates my config.
 */
public class MyConfigFactory {

    private final MyConfig myConfig = new MyConfig("initial");

    public MyConfig getConfig() {
        synchronized (myConfig) {
            return new MyConfig(myConfig.getTheValue());
        }
    }

    public void updateMyConfig(MyConfig config) {
        synchronized (myConfig) {
            myConfig.setTheValue(config.getTheValue());
        }
    }
}
