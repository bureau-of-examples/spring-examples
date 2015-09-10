package zhy2002.springexamples.common;

import javax.validation.constraints.Min;

/**
 * An object used to test validation message interpolation.
 */
public class ValidationTestObject {

    @Min(1)
    private Integer greaterThan0;

    @Min(value = 2, message = "At least is 2.")
    private Integer greaterThan1;

    @Min(value = 3, message = "Cannot be smaller than {value}.")
    private Integer greaterThan2;

    @Min(value = 4, message = "{ValidationTestObject.greaterThan3.Min.message}")
    private Integer greaterThan3;

    public Integer getGreaterThan0() {
        return greaterThan0;
    }

    public void setGreaterThan0(Integer greaterThan0) {
        this.greaterThan0 = greaterThan0;
    }

    public Integer getGreaterThan1() {
        return greaterThan1;
    }

    public void setGreaterThan1(Integer greaterThan1) {
        this.greaterThan1 = greaterThan1;
    }

    public Integer getGreaterThan2() {
        return greaterThan2;
    }

    public void setGreaterThan2(Integer greaterThan2) {
        this.greaterThan2 = greaterThan2;
    }

    public Integer getGreaterThan3() {
        return greaterThan3;
    }

    public void setGreaterThan3(Integer greaterThan3) {
        this.greaterThan3 = greaterThan3;
    }
}
