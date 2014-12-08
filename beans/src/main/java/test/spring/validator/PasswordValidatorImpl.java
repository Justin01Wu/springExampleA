package test.spring.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidatorImpl implements
		ConstraintValidator<PasswordValidator, String> {
	
	public static final String errorMsg = "8-30 characters and contains at least 1 number, 1 uppercase, and 1 lowercase character";
	
	@Override
	public void initialize(PasswordValidator arg0) {
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

		Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}");
		Matcher matcher = pattern.matcher(codeValue);
		if (!matcher.matches()) {
			ctx.disableDefaultConstraintViolation();

			// ctx.buildConstraintViolationWithTemplate("{code.contain.only}")
			// .addConstraintViolation();

			ctx.buildConstraintViolationWithTemplate(errorMsg).addConstraintViolation();

			return false;

		} else {

			return true;

		}

	}

}
