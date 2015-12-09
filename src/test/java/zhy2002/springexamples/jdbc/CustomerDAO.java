package zhy2002.springexamples.jdbc;

/**
 * Created by JOHNZ on 9/12/2015.
 */
public interface CustomerDAO {

    int getCustomerCount();

    void queryNonExistentTable();

    int getFirstCustomerId();

    int updateCustomerName(int customerId, String newName);

    String getCustomerName(int customerId);
}
