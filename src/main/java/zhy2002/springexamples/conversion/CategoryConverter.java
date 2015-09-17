package zhy2002.springexamples.conversion;

import org.springframework.core.convert.converter.Converter;
import zhy2002.springexamples.domain.Category;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock converter from string to category.
 */
public class CategoryConverter implements Converter<String, Category> {

    private static final Map<String, Category> predefinedCategories = new HashMap<>();

    static {
        Category internationalNews = new Category();
        internationalNews.setId(3);
        internationalNews.setName("International News");
        predefinedCategories.put("International_News", internationalNews);
    }

    @Override
    public Category convert(String source) {
        if(source == null)
            return null;

        Category result = predefinedCategories.get(source);
        if(result == null)
            throw new IllegalArgumentException();

        return result;
    }
}
