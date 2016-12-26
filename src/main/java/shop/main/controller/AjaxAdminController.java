package shop.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.main.utils.URLUtils;

@Controller
public class AjaxAdminController {	
	
	@RequestMapping(value = "a/translit")
	public @ResponseBody String getTranslitURL(@RequestBody String name) {
		System.out.println("request is "+name);
		String result = URLUtils.transliterate(name.substring(6));
		//logic
		return result;
	}
	
}
