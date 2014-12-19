package test.spring.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;

import test.spring.validator.PasswordValidator;
import test.spring.validator.UserNameValidator;

//@Component // Spring traditional way to set an entity

// if your code can support json but can NOT support XML, 
// it means you need add @XmlRootElement to your returned bean
public class Person {
	
	private Integer id;
	
	private String name;
	private Date birth;	
	private transient String password;
	
	private List<String> emails = new ArrayList<String>();
	
	public Person(){
	}
	
	public Person(Integer id, String name, String password){
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@UserNameValidator
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@PasswordValidator
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}	
	
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emailList) {
		this.emails = emailList;
	}
	public void addEmails(String email) {
		this.emails.add(email);
	}
	
	@PreDestroy
	public void cleanUp() throws Exception {
		System.out.println("");
		System.out.println("     ==>  Spring Container is destroy! Person clean up");
	}	
	

}
