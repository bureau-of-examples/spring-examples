package zhy2002.springexamples;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheTestBean {

    private int id = 0;

    @Cacheable("id")
    public int getNextId(){
        return ++id;
    }

}
