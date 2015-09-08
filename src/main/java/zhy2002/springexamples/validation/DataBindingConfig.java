package zhy2002.springexamples.validation;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class DataBindingConfig {

    @Bean
    public CustomerValidator customerValidator(){
        return new CustomerValidator();
    }

    @Bean
    public ShoppingCartValidator shoppingCartValidator(){

        return new ShoppingCartValidator();
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("databinding/validationMessages");
        return messageSource;
    }

    @Bean
    public MessageSource messageSource2(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("databinding/validationMessages2");
        return messageSource;
    }
}
