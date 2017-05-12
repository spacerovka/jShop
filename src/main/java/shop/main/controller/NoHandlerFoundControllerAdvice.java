package shop.main.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NoHandlerFoundControllerAdvice {
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
		System.out.println(e);
		return new ModelAndView("404", "message", "We are sorry, but the page you requested was not found.");
		
	}
}
