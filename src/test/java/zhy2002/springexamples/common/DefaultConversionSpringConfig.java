package zhy2002.springexamples.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import zhy2002.springexamples.conversion.ProductCartItemConverter;


@Configuration
public class DefaultConversionSpringConfig {

    @Bean
    public ConversionService conversionService(){
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new ProductCartItemConverter());
        return conversionService;
    }
}
