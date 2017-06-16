package shop.main.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	private Pattern pattern;
	private Matcher matcher;
	private static final String PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";

	@Override
	public void initialize(ValidPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		return (validate(password));
	}

	private boolean validate(String password) {
		pattern = Pattern.compile(PATTERN);
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
