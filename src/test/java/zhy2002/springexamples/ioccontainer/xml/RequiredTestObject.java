package zhy2002.springexamples.ioccontainer.xml;


import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotNull;

/**
 * Test the at Required annotation.
 */
public class RequiredTestObject {

    private String optionalProperty;
    private String requiredProperty;

    public String getOptionalProperty() {
        return optionalProperty;
    }

    public void setOptionalProperty(String optionalProperty) {
        this.optionalProperty = optionalProperty;
    }

    @NotNull
    public String getRequiredProperty() {
        return requiredProperty;
    }

    @Required
    public void setRequiredProperty(String requiredProperty) {
        this.requiredProperty = requiredProperty;
    }
}
