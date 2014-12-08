package test.spring.beans;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

	@Size(min=5,max=30)
	public String firstName;
	
	@Pattern(regexp = "[a-z-A-Z]*", message = "Last name has invalid characters")
	public String lastName;
	public Integer id;
	public String passWord;
	public String confirmPassword;

	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}