package zhy2002.springexamples.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by JOHNZ on 1/09/2015.
 */
public class MaxFinder {

    public int findMaxConcurrently(final List<Integer> numbers, ExecutorService threadPool, final int batchSize) throws Exception{
        int numBatch = (numbers.size() - 1) / batchSize + 1;
        List<Callable<Integer>> tasks = new ArrayList<>();
        for(int i=0; i<numBatch; i++){
            tasks.add(new FindMaxInRange(numbers, batchSize * i, batchSize));
        }
        Collection<Future<Integer>> results = threadPool.invokeAll(tasks);
        int max = Integer.MIN_VALUE;
        for(Future<Integer> future : results){
            if(!future.isDone())
                throw new RuntimeException("what the what?");
            if(future.get() > max){
                max = future.get();
            }
        }
        return max;
    }
}
