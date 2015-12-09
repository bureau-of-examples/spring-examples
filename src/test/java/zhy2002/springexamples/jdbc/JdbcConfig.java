package zhy2002.springexamples.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@EnableTransactionManagement
@Configuration
public class JdbcConfig {

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:h2:file:./data/simpleshop;create=false;");
        dataSource.setUsername("dbo");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public CustomerDAO getCustomerDAO(){
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        customerDAO.init(getDataSource());
        return customerDAO;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(getDataSource());
        dataSourceTransactionManager.setDefaultTimeout(60);
        return dataSourceTransactionManager;
    }

    @Bean
    public CustomerService getCustomerService(){
        return new CustomerServiceImpl();
    }
}
