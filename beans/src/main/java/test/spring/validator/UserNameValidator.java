package test.spring.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) 
@Constraint(validatedBy=UserNameValidatorImpl.class)
public @interface UserNameValidator {
	
	String message() default "accepts alpha-numeric values only";	 	 
	 
    Class<?>[] groups() default {}; 
 
    Class<? extends Payload>[] payload() default {};
}
