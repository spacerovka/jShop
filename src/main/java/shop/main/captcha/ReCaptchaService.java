package shop.main.captcha;

public interface ReCaptchaService {
	void processResponse(final String response) throws ReCaptchaInvalidException;

	String getReCaptchaSite();

	String getReCaptchaSecret();
}
