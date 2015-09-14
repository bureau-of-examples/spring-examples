package zhy2002.springexamples.common;

import org.springframework.beans.factory.BeanNameAware;

import java.util.ArrayList;
import java.util.List;

/**
 * A bean designed to test lazy init feature of Spring container.
 */
public class LazyTestObject implements BeanNameAware {

    private static List<String> logs = new ArrayList<>();

    public static List<String> getLogs() {
        return logs;
    }

    public static void setLogs(List<String> logs) {
        LazyTestObject.logs = logs;
    }

    @Override
    public void setBeanName(String name) {
        logs.add(name);
    }
}
