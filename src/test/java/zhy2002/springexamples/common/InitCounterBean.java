package zhy2002.springexamples.common;

/**
 * Count number of instances since reset.
 */
public class InitCounterBean {

    private static int INSTANCE_COUNT = 0;

    public static int getInstanceCount(){
        return INSTANCE_COUNT;
    }

    public static void reset(){
        INSTANCE_COUNT = 0;
    }

    public InitCounterBean(){
        synchronized (InitCounterBean.class){
            INSTANCE_COUNT++;
        }

    }




}
