package test.spring.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import test.spring.beans.User;

// it comes from http://java-assist.blogspot.ca/2012/08/example-on-validation-with-spring-mvc.html
public class UserValidator implements Validator {
	 
	 @Override
	 public boolean supports(Class arg0) {
	  return User.class.equals(arg0);
	 }
	 
	 @Override
	 public void validate(Object obj, Errors e) {
	   ValidationUtils.rejectIfEmpty(e, "firstName", "firstName.empty");
	   ValidationUtils.rejectIfEmpty(e, "lastName", "lastName.empty");
	   ValidationUtils.rejectIfEmpty(e, "passWord", "passWord.empty");
	   String defaultMsg= "confirmPassword can't be null";
	   ValidationUtils.rejectIfEmptyOrWhitespace(e, "confirmPassword", "confirmPassword.empty", defaultMsg);
	    
	   User user = (User)obj;
	   if(user.passWord != null && !user.passWord.equals(user.confirmPassword)){
	    e.rejectValue("confirmPassword","confirmPassword.notequal","confirmPassword must equals passWord");
	   }
	 }
	 
	}