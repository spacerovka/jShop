package shop.main.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CaptchaSettings {

	@Autowired
	private Environment environment;

	private String site;
	private String secret;

	public String getSite() {
		return environment.getProperty("google.recaptcha.key.site");
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSecret() {
		return environment.getProperty("google.recaptcha.key.secret");
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
