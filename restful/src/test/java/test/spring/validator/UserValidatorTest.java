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

//		// bind to the target object
//		binder.bind(propertyValues);

		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		BindingResult results = binder.getBindingResult();
		assertEquals(results.getAllErrors().size(), 0 );
	}
	
	public void testInvalidUser() {
		User user = new User();
		user.setFirstName("Justin");
		
		DataBinder binder = new DataBinder(user);
		binder.setValidator(new UserValidator());

//		// bind to the target object
//		binder.bind(propertyValues);

		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		BindingResult results = binder.getBindingResult();
		assertEquals(results.getAllErrors().size(), 3 );
	}
	
	
	public void testInvalidUser2() {
		User user = new User();
		user.setFirstName("Justin");
		
		BindException errors = new BindException(user, "user");
		
		UserValidator validator = new UserValidator();
		
		validator.validate(user, errors);

		assertEquals(errors.getFieldErrorCount(), 3 );
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
		user.setLastName("Justin719");	// can't have number
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();		
		Validator validator = validatorFactory.getValidator();		
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		assertEquals(violations.size(), 1 );
	}

}
