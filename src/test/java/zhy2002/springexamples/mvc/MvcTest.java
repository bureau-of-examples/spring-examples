package zhy2002.springexamples.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import zhy2002.springexamples.domain.Article;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test a controller.
 */
@WebAppConfiguration
@ContextConfiguration("classpath:mvctest/springMvcConfig.xml")
public class MvcTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void beforeMethod(){

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getActionShouldWork() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(get("/hello/{name}", "Jane"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(model().attribute("name", "Jane"));

    }

    @Test
    public void postActionShouldWork() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(post("/accessSecret").param("username", "Ned").param("password", "Stark"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(model().attribute("secret", "Revealed"));
    }


    @Test
    public void canFindViewInFolder() throws Exception{

        //arrange

        //action
        ResultActions resultActions = mockMvc.perform(get("/articles/{id}", 5L));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(model().attribute("id", 5L));
        resultActions.andExpect(view().name("articles/readArticle"));
    }

    @Test
    public void canMatchMoreSpecificUrl() throws Exception {

        //action
        ResultActions resultActions = mockMvc.perform(get("/articles/edit/{id}", 5L));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(model().attribute("id", 5L));
        resultActions.andExpect(view().name("articles/editArticle"));
    }

    @Test
    public void canAccessStaticResources() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(get("/css/layout.css"));

        //assertion
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void cannotPostToGetArticle() throws Exception{
        //action
        ResultActions resultActions = mockMvc.perform(post("/articles/{id}", 5L));

        //assertion
        resultActions.andExpect(status().is(405));
    }

    @Test
    public void canRedirect() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(post("/articles/save").param("title","A new entry").param("content","Something I wrote"));

        //assertion
        resultActions.andExpect(redirectedUrl("999"));
        resultActions.andExpect(model().attribute("article", hasProperty("title", equalTo("A new entry"))));
    }
}
