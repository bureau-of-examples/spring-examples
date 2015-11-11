package zhy2002.springexamples.console;


import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import zhy2002.springexamples.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class Program {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("console_spring.xml");//ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});

        testDI(applicationContext);

        //testClassPathResource(applicationContext);

        //testSpEL();
        //testLeetcode();

        CacheTestBean idProvider = applicationContext.getBean(CacheTestBean.class);
        for(int i=0; i<10; i++){
            System.out.println(idProvider.getNextId());
            Thread.sleep(1000);
        }


    }



    private static void testLeetcode() {
        //System.out.println(rangeBitwiseAnd(5, 7));

        //System.out.println(isHappy(19));
        //System.out.println(isIsomorphic("ab", "ca"));


//        char[][] grid =  {{'1','1','0','0','0'},
//        {'1','1','0','0','0'},
//        {'0','0','1','0','0'},
//        {'0','0','0','1','1'}};

//        char[][] grid = {
//                {'1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '0', '1', '1'},
//                {'0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '0'},
//                {'1', '0', '1', '1', '1', '0', '0', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '0', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1'},
//                {'0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '0', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '0', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1'},
//                {'1', '0', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1', '1', '0', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '0'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '0', '0'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
//                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'}};

        char[][] grid ={{'1','1','1'},{'0','1','0'},{'1','1','1'}};
        System.out.println(numIslands(grid));
    }


    public static int numIslands(char[][] grid) {

        if (grid.length == 0)
            return 0;
        int n = grid.length;
        if (grid[0].length == 0)
            return 0;
        int m = grid[0].length;

        boolean[][] flags = new boolean[n][m];
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != '1' || flags[i][j])
                    continue;

                bfs(grid, n, m, i, j, flags);   //avoid stack overflow
                count++;
            }
        }
        return count;
    }

    private static void bfs(char[][] grid, int n, int m, int i, int j, boolean[][] flags) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(i);
        queue.add(j);
        flags[i][j] = true;

        do {
            int r = queue.poll();
            int c = queue.poll();

            int rNext = r - 1;
            int cNext = c;
            if (rNext >= 0 && grid[rNext][cNext] == '1' && !flags[rNext][cNext]) {
                queue.add(rNext);
                queue.add(cNext);
                flags[rNext][cNext] = true;
            }

            rNext = r;
            cNext = c - 1;
            if (cNext >= 0 && grid[rNext][cNext] == '1' && !flags[rNext][cNext]) {
                queue.add(rNext);
                queue.add(cNext);
                flags[rNext][cNext] = true;
            }

            rNext = r + 1;
            cNext = c;
            if (rNext < n && grid[rNext][cNext] == '1' && !flags[rNext][cNext]) {
                queue.add(rNext);
                queue.add(cNext);
                flags[rNext][cNext] = true;
            }

            rNext = r;
            cNext = c + 1;
            if (cNext < m && grid[rNext][cNext] == '1' && !flags[rNext][cNext]) {
                queue.add(rNext);
                queue.add(cNext);
                flags[rNext][cNext] = true;
            }

        } while (!queue.isEmpty());
    }







    private static void testSpEL() {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Zhang");
        person.setDob(new Date());

        String fullName = expressionParser.parseExpression("firstName + ' ' + lastName").getValue(new StandardEvaluationContext(person), String.class);
        System.out.println(fullName);
    }

    private static void testDI(ApplicationContext applicationContext) {
        MessageHolder helloMessage = (MessageHolder) applicationContext.getBean("helloMessage");
        System.out.println(helloMessage.getMessage());
        MessagePrinter helloPrinter = (MessagePrinter) applicationContext.getBean("helloPrinter");
        helloPrinter.print();

        MessageBoard messageBoard = applicationContext.getBean("messageBoard", MessageBoard.class);
        messageBoard.print();

        PropertyPlaceholderConfigurer properties = (PropertyPlaceholderConfigurer) applicationContext.getBean("myProperties");
        System.out.println(properties.getClass().toString());
    }

    private static void testClassPathResource(ApplicationContext applicationContext) throws IOException {
        Resource resource = applicationContext.getResource("logback.xml");
        System.out.println(resource.exists());
        URL url = resource.getURL();
        System.out.println(url.getContent());

        URI uri = resource.getURI();
        System.out.println(uri.getRawPath());

        Scanner scanner = new Scanner(resource.getInputStream());
        while (scanner.hasNextLine())
            System.out.println(scanner.nextLine());
    }
}
