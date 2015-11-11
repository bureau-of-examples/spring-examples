package zhy2002.springexamples.common;


public class MyTestServiceImpl implements MyTestService {
    @Override
    public int getIdentityHashCode() {
        return this.hashCode();
    }
}
