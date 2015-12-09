package zhy2002.springexamples.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Transactional
    public void updateAndException(String newName){

        int id = customerDAO.getFirstCustomerId();
        assertThat(id, greaterThan(0));

        int rowEffected = customerDAO.updateCustomerName(id, newName);
        assertThat(rowEffected, equalTo(1));

        throw new RuntimeException("See if it rowed back");

    }
}
