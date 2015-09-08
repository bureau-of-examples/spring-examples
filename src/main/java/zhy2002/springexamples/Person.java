package zhy2002.springexamples;

import java.util.Date;

/**
 * Created by JOHNZ on 6/05/2015.
 */
public class Person {

    private String firstName;
    private String lastName;
    private Date dob;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
