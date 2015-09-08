package zhy2002.springexamples.testing;

import org.testng.annotations.Test;
import zhy2002.springexamples.algorithm.MaxFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JOHNZ on 1/09/2015.
 */
public class MaxFinderTest {

    @Test
    public void findMaxTest() throws Exception{
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        Random random = new Random(1);
        List<Integer> numbers = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for(int i=0; i< 1000; i++){
            int val = random.nextInt();
            numbers.add(val);
            if(val > max)
                max = val;
        }
        numbers = Collections.unmodifiableList(numbers);
        long time = System.currentTimeMillis();
        int concurrentMax = new MaxFinder().findMaxConcurrently(numbers, threadPool, 10);
        System.out.println("Seconds took " + (double)(System.currentTimeMillis() - time) / 1000);
        if(concurrentMax != max)
            throw new RuntimeException();

        threadPool.shutdown();
    }
}
