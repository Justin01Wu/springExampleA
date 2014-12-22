package test.spring.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import junit.framework.TestCase;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import test.spring.beans.User;

public class UserValidatorTest extends TestCase {
	
	public void testValidUser() {
		User user = new User();
		user.setFirstName("Justin");
		user.setLastName("Wu");
		user.setPassWord("Abcd");
		user.setConfirmPassword("Abcd");
		
		DataBinder binder = new DataBinder(user);
		binder.setValidator(new UserValidator());

		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		BindingResult results = binder.getBindingResult();
		assertEquals(results.getAllErrors().size(), 0 );
	}
	
	public void testInvalidUserLastName() {
		User user = new User();
		user.setFirstName("Justin");
		user.setPassWord("Abcd");
		user.setConfirmPassword("Abcd");
		
		DataBinder binder = new DataBinder(user);
		binder.setValidator(new UserValidator());

		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		BindingResult results = binder.getBindingResult();
		assertEquals(results.getAllErrors().size(), 1 );
		assertEquals(results.getTarget().getClass(), User.class );
		ObjectError error = results.getAllErrors().get(0);
		assertEquals(error.getClass(), FieldError.class );
		FieldError error2 = (FieldError)error;		
		assertEquals("lastName should not be empty", error2.getField(), "lastName" );
		assertEquals("lastName should not be empty", error2.getCode(), "lastName.empty" );
		
	}
	
	public void testInvalidUserPassword() {
		User user = new User();
		user.setFirstName("Justin");
		user.setLastName("Wu");
		user.setConfirmPassword("Abcd");
		
		DataBinder binder = new DataBinder(user);
		binder.setValidator(new UserValidator());

		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		BindingResult results = binder.getBindingResult();
		assertEquals(results.getAllErrors().size(), 1 );
		assertEquals(results.getTarget().getClass(), User.class );
		ObjectError error = results.getAllErrors().get(0);
		assertEquals(error.getClass(), FieldError.class );
		FieldError error2 = (FieldError)error;		
		assertEquals("password should not be empty", error2.getField(), "passWord" );
		assertEquals("password should not be empty", error2.getCode(), "passWord.empty" );
		
	}	
	
	public void testInvalidUser2() {
		User user = new User();
		user.setFirstName("Justin");
		
		BindException errors = new BindException(user, "user");
		
		UserValidator validator = new UserValidator();
		
		validator.validate(user, errors);

		assertEquals(errors.getFieldErrorCount(), 3 );
		
		for(ObjectError error: errors.getAllErrors()){
			System.out.println(error.getObjectName());
		}
	}
	
	public void testValidUserOnJSR303() {
		User user = new User();
		user.setLastName("Justin");
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();		
		Validator validator = validatorFactory.getValidator();		
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		assertEquals(violations.size(), 0 );
	}
	
	public void testInvalidUserOnJSR303() {
		User user = new User();
		user.setLastName("Justin719");	
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();		
		Validator validator = validatorFactory.getValidator();		
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		assertEquals("lastName can't have number", violations.size(), 1 );
		ConstraintViolation<User> result = violations.iterator().next();
		 
		assertEquals(result.getRootBean().getClass(), User.class );
		assertEquals(result.getMessage(), "Last name has invalid characters" );
		
	}

}
