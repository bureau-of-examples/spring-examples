package zhy2002.springexamples.domain;

/**
 * Created by JOHNZ on 11/05/2015.
 */
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean vip;

    public Customer(){
        System.out.println("Creating customer...");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public static class CustomerFactory{

        public static Customer createNew(String firstName, String lastName){
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            return customer;
        }
    }
}
