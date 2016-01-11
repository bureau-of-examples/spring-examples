package zhy2002.springexamples.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

    @Bean
    public AsyncClass getAsyncClass(){
        return new AsyncClass(getMessageBag().getItems());
    }

    @Bean
    public MessageBag getMessageBag(){
       return new MessageBag();
    }

}
