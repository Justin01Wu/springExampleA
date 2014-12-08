package test.spring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import test.spring.beans.Address;
import test.spring.beans.Customer;
import test.spring.service.CustomerManager;
import test.spring.service.CustomerManagerImpl;

/** 
 * this examples comes from 
 * http://www.journaldev.com/2603/spring-transaction-management-example-with-jdbc 
 *
 */
@Component
public class TransactionManagerTest {
	
	@Value( "${jdbc.url}" )
	private String jdbcUrl;
	
	@BeforeClass
	public static void prepareTables() throws ClassNotFoundException, SQLException{
		
        Class.forName("org.h2.Driver");  // load the driver class
        
        // this one doesn't work for static method, TODO : fix it
        //System.out.println(  "    ===>>>    jdbcUrl= " + jdbcUrl);
        
        
        // connect to a in memory database, because of H2 feature, you don't need to install db sever or create db before do this 
        Connection con = DriverManager.getConnection("jdbc:h2:mem:mytest", "sa", "");  // mytest is database name
        
        // here you create the table
        String createCustomer = "CREATE TABLE Customer (id INTEGER NOT NULL PRIMARY KEY, name varchar(20))";
        Statement sst = con.createStatement();
        sst.executeUpdate(createCustomer);
        
        String createAddress = "CREATE TABLE Address (id INTEGER NOT NULL PRIMARY KEY, address varchar(20), country varchar(20))";
        sst = con.createStatement();
        sst.executeUpdate(createAddress); 

	}
	
	@Test  (expected = DataIntegrityViolationException.class)
	public void testAddressTooLongFailure() {
		
		// didn't get jdbcUrl, even this class is @Component, TODO: fix it
		System.out.println(  "    ===>>>    jdbcUrl= " + jdbcUrl);
		
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
 
        CustomerManager customerManager = ctx.getBean("customerManager", CustomerManagerImpl.class);
 
        Address address = createDummyAddress(1);
        // setting value more than 20 chars, so that SQLException occurs: DataIntegrityViolationException
        address.setAddress("Oakville, On Canada, L6M 0V8");
        Customer cust = createDummyCustomer(address, 1);
        customerManager.createCustomer(cust);
 
        ctx.close();
	}
	
	@Test (expected = DuplicateKeyException.class)
	public void testIdDuplicatedFailure() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
 
        CustomerManager customerManager = ctx.getBean("customerManager", CustomerManagerImpl.class);
 
        Address address = createDummyAddress(2);
        Customer cust = createDummyCustomer(address, 2);
        
        customerManager.createCustomer(cust);        
        customerManager.createCustomer(cust);  // create duplicated customer will fail with DuplicateKeyException
 
        ctx.close();
	}
	
	@Test
	public void testTransactionSuccess() {
		
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");        
        CustomerManager customerManager = ctx.getBean("customerManager", CustomerManagerImpl.class);

        Address address = createDummyAddress(3);
        Customer cust = createDummyCustomer(address, 3);
        customerManager.createCustomer(cust);
 
        ctx.close();

        
	}

	
	private static Address createDummyAddress(Integer id) {
        Address address = new Address();
        address.setId(id);
        address.setCountry("Canada");        
        address.setAddress("Oakville");		
        return address;
	}
	
    private static Customer createDummyCustomer(Address address, Integer id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Justin");
        customer.setAddress(address);
        return customer;
    }
}
