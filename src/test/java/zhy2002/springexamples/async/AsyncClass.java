package zhy2002.springexamples.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import zhy2002.springexamples.testing.TestUtil;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * A class with async methods.
 */
public class AsyncClass {

    private List<String> synchronizedList;

    public AsyncClass(List<String> synchronizedList){
       this.synchronizedList = synchronizedList;
    }

    @Async
    public void addString(String value) {

        TestUtil.wait(50);
        synchronizedList.add(value);
    }

    @Async
    public Future<String> toLower(String value){

        TestUtil.wait(50);
        synchronizedList.add(value);
        return new AsyncResult<>(value.toLowerCase());

    }

}
