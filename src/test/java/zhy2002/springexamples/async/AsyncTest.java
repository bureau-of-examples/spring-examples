package zhy2002.springexamples.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import zhy2002.springexamples.testing.TestUtil;

import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test Spring Async features.
 */
@ContextConfiguration(classes = {AsyncConfig.class})
public class AsyncTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AsyncClass asyncClass;

    @Autowired
    private MessageBag messageBag;

    @Test
    public void atAsyncMethodsAreExecutedAsynchronously(){

        assertThat(asyncClass, notNullValue());
        messageBag.getItems().clear();
        asyncClass.addString("test1");
        assertThat(messageBag.getItems(), hasSize(0));

        TestUtil.wait(100);
        assertThat(messageBag.getItems(), hasSize(1));
    }

    @Test
    public void asyncMethodCanReturnValue() throws Exception{

        messageBag.getItems().clear();
        Future<String> result = asyncClass.toLower("BASIC");
        assertThat(messageBag.getItems(), hasSize(0));
        assertThat(result.get(), equalTo("basic"));
        assertThat(messageBag.getItems(), hasSize(1));
    }
}
