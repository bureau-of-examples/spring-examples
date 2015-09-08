package zhy2002.springexamples.testing;

import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class MockObjectsTest {

    @Test
    public void mockEnvironmentTest(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("PC-NAME", "PC1234");
        assertEquals(environment.getProperty("PC-NAME"), "PC1234");
    }

    @Test
    public void mockPropertySourceTest(){
        MockPropertySource propertySource = new MockPropertySource();
        propertySource.setProperty("MIN-SCORE",75);
        assertEquals(propertySource.getProperty("MIN-SCORE"), 75);
    }




}
