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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test a controller.
 */
@WebAppConfiguration
@ContextConfiguration("classpath:mvctest/mvcXmlTest.xml")
public class MvcXmlTest extends AbstractTestNGSpringContextTests {

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
        resultActions.andExpect(view().name("hello"));
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
    public void canConvertIfTargetTypeHasStringConstructor() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(post("/hello/changeColor/red"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(result -> result.getResponse().getContentAsString().equals("Color is changed to red"));
    }

    @Test
    public void canConvertWithConverter() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(post("/articles/changeCategory/International_News"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(result -> result.getResponse().getContentAsString().equals("Category is changed to 3"));
    }



    @Test
    public void canAutowireControllerConstructor() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(get("/countCartItems"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(result -> result.getResponse().getContentAsString().equals("3"));
    }

    @Test
    public void canReturnMapAsModel()  throws Exception{
        //action
        ResultActions resultActions = mockMvc.perform(get("/allCarts"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(model().attributeExists("1"));
        resultActions.andExpect(model().attributeExists("2"));
        resultActions.andExpect(view().name("allCarts"));

    }

    @Test
    public void canUseMatrixVariable() throws Exception{
        //action
        ResultActions resultActions = mockMvc.perform(get("/hello2/john;times=3"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(result -> result.getResponse().getContentAsString().equals("john,john,john"));


    }

    @Test
    public void mustProvideRequiredMatrixVariable() throws Exception{
        //action
        ResultActions resultActions = mockMvc.perform(get("/hello2/john"));

        //assertion
        resultActions.andExpect(status().is(400));
    }

    @Test
    public void canUsePropertyPlaceHolderInPath() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(get("/hello/heymate"));

        //assertion
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(result -> result.getResponse().getContentAsString().equals("success"));
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
        resultActions.andExpect(model().attribute("controllerName", "articleController")); //@ModelAttribute method adds this attribute to all action methods.
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
    public void canRedirectWithSimpleTypeModelAttributes() throws Exception{

        //action
        ResultActions resultActions = mockMvc.perform(post("/articles/save").param("title","A new entry").param("content","Something I wrote"));

        //assertion
        resultActions.andExpect(redirectedUrl("999?controllerName=articleController")); //model attributes are passed along with redirect
        resultActions.andExpect(model().attribute("article", hasProperty("title", equalTo("A new entry"))));
        resultActions.andExpect(model().attribute("controllerName", "articleController"));
    }
}
