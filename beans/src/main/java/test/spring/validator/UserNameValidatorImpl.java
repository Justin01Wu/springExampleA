package test.spring.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidatorImpl implements
		ConstraintValidator<UserNameValidator, String> {
	
	public static final String errorMsg = "accepts 5-30 alpha-numeric characters only";
	
	
	@Override
	public void initialize(UserNameValidator arg0) {
	}

	@Override
	public boolean isValid(String codeValue, ConstraintValidatorContext ctx) {
		return checkNull(codeValue, ctx) && checkMatched(codeValue, ctx);
	}

	private boolean checkNull(String codeValue, ConstraintValidatorContext ctx) {

		boolean isValid;
		if (codeValue == null || codeValue.equals("")) {
			isValid = false;
		} else {
			isValid = true;
		}

		if (!isValid) {
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate(errorMsg).addConstraintViolation();

		}
		return isValid;

	}

	private boolean checkMatched(String codeValue,
			ConstraintValidatorContext ctx) {

		Pattern pattern = Pattern.compile("[0-9a-zA-Z]{5,30}");
		Matcher matcher = pattern.matcher(codeValue);
		if (!matcher.matches()) {
			ctx.disableDefaultConstraintViolation();

//			ctx.buildConstraintViolationWithTemplate("{code.contain.only}")
//					.addConstraintViolation();
			
			ctx.buildConstraintViolationWithTemplate(errorMsg).addConstraintViolation();
			
			

			return false;

		} else {

			return true;

		}

	}

}
