package zhy2002.springexamples.common;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

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
    private ArrayList<Integer> integers;

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

    public ArrayList<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(ArrayList<Integer> integers) {
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
}
