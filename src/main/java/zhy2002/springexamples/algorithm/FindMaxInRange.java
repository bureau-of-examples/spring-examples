package zhy2002.springexamples.algorithm;

import java.util.List;
import java.util.concurrent.Callable;

public class FindMaxInRange implements Callable<Integer> {

    private final List<Integer> list;
    private  final int start;
    private final int size;

    public FindMaxInRange(List<Integer> list, int start, int size){
        this.list = list;
        this.start = start;
        this.size = size;
    }

    @Override
    public Integer call() throws Exception {
        if(size <= 0 || start >= list.size())
            throw new IllegalArgumentException();

        int end = Math.min(start + size, list.size()) - 1;
        int max = list.get(start);
        for(int i=start + 1; i <= end ; i++){
            if(list.get(i) > max){
                max = list.get(i);
            }
        }
        return max;
    }
}

