package zhy2002.springexamples.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;

import java.util.List;

/**
 * Util class to load converters into the conversion service.
 */
public class ConfigurableConversionServiceInitializer {

    private ConfigurableConversionService conversionService;
    private List<Converter<?, ?>> converters;

    public ConfigurableConversionServiceInitializer(ConfigurableConversionService conversionService){
       this.conversionService = conversionService;
    }

    public List<Converter<?, ?>> getConverters() {
        return converters;
    }

    public void setConverters(List<Converter<?, ?>> converters) {
        this.converters = converters;
        if(converters != null){
            converters.forEach(conversionService::addConverter);
        }
    }
}
