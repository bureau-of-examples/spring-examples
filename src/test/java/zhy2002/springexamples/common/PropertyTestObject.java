package zhy2002.springexamples.common;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * An object whose properties are used in tests.
 */
public class PropertyTestObject {

    private Class<?> clazz;
    private Boolean real;
    private File baseFile;
    private Locale uiLocale;
    private URL website;
    private Color backgroundColor;
    private List<Integer> integers;
    private Properties configProperties;
    private String beanId;
    private PropertyTestObject nestedTestObject;

    public PropertyTestObject(){}

    public PropertyTestObject(PropertyTestObject nestedTestObject){
        this.nestedTestObject = nestedTestObject;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Boolean getReal() {
        return real;
    }

    public void setReal(Boolean real) {
        this.real = real;
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    public File getBaseFile() {
        return baseFile;
    }

    public void setBaseFile(File baseFile) {
        this.baseFile = baseFile;
    }

    public Locale getUiLocale() {
        return uiLocale;
    }

    public void setUiLocale(Locale uiLocale) {
        this.uiLocale = uiLocale;
    }

    public URL getWebsite() {
        return website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Properties getConfigProperties() {
        return configProperties;
    }

    public void setConfigProperties(Properties configProperties) {
        this.configProperties = configProperties;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public PropertyTestObject getNestedTestObject() {
        return nestedTestObject;
    }

    public void setNestedTestObject(PropertyTestObject nestedTestObject) {
        this.nestedTestObject = nestedTestObject;
    }
}
