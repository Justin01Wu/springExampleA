package test.spring.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) 
@Constraint(validatedBy=PasswordValidatorImpl.class)
public @interface PasswordValidator {
	
	String message() default " 8-30 characters and contains at least 1 number, 1 uppercase, and 1 lowercase character";	 	 
	 
    Class<?>[] groups() default {}; 
 
    Class<? extends Payload>[] payload() default {};

}
