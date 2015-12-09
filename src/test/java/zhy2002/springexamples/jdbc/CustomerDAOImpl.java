package zhy2002.springexamples.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getCustomerCount() {
        return jdbcTemplate.execute((StatementCallback<Integer>) stmt -> {
            ResultSet resultSet = stmt.executeQuery("select count(*) from contacts");
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    public void queryNonExistentTable() {
        jdbcTemplate.execute("select * from NonExistentTable");
    }


    public int getFirstCustomerId() {
        return jdbcTemplate.execute((StatementCallback<Integer>) stmt -> {
            ResultSet resultSet = stmt.executeQuery("select top 1 id from contacts");
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    public int updateCustomerName(int customerId, String newName) {

        return jdbcTemplate.execute(
                (StatementCallback<Integer>) stmt -> stmt.executeUpdate("update contacts set name = '" + newName + "' where id = " + customerId)
        );

    }

    public String getCustomerName(int customerId){
        return jdbcTemplate.execute((StatementCallback<String>) stmt -> {
            ResultSet resultSet = stmt.executeQuery("select name from contacts where id = '" + customerId + "'");
            resultSet.next();
            return resultSet.getString(1);
        });
    }


}
