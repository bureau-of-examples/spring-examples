package zhy2002.springexamples.common;

@FunctionalInterface
public interface ThrowingConsumer<T> {


    void accept(T arg) throws Exception;
}
