package zhy2002.springexamples.testing;

import org.testng.annotations.Test;
import zhy2002.springexamples.algorithm.Combinator;
import zhy2002.springexamples.algorithm.VisitListCallback;

import java.util.ArrayList;

public class CombinatorTest {


    @Test
    public void test(){
        System.out.println("Combination test allowing duplicates:");
        testCombination(1);
        testCombination(2);
        testCombination(3);
    }

    private static void testCombination(int selectCount)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=1; i<=5;i++)
            list.add(i);

        Combinator<Integer> algorithm = new Combinator<Integer>();
        algorithm.combination(
                list,
                selectCount,
                new VisitListCallback<ArrayList<Integer>>() {
                    @Override
                    public boolean Visit(ArrayList<Integer> list) {
                        System.out.println(list);
                        return true;
                    }
                }
        );


    }
}
