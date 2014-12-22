package test.spring.service;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import test.spring.beans.Customer;

public class CustomerDAOImpl implements CustomerDAO {
 
    private DataSource dataSource;
 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 
    @Override
    public void create(Customer customer) {
    	
        String queryCustomer = "insert into Customer (id, name) values (?,?)";
        String queryAddress = "insert into Address (id, address,country) values (?,?,?)";
 
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
 
        try {
			String jdbcUrl = dataSource.getConnection().getMetaData().getURL();
			System.out.println(" ==>> Jdbc URL = " + jdbcUrl);
		} catch (SQLException e) {
			throw new RuntimeException("can't get connection", e);
		}
        jdbcTemplate.update(queryCustomer, new Object[] { customer.getId(), customer.getName() });
        System.out.println("   ==>> Inserted into Customer Table Successfully");
        
        jdbcTemplate.update(queryAddress, new Object[] { customer.getId(),
                customer.getAddress().getAddress(),
                customer.getAddress().getCountry() });
        System.out.println("   ==>> Inserted into Address Table Successfully");
    }
 
}