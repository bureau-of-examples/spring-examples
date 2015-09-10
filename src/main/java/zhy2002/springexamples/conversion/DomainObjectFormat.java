package zhy2002.springexamples.conversion;


import java.lang.annotation.*;

/**
 * Decides how the domain object should be formatted to and from string.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface DomainObjectFormat {
}
