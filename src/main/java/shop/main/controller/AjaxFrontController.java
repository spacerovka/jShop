package shop.main.controller;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shop.main.data.objects.Review;
import shop.main.data.service.CategoryService;
import shop.main.data.service.ProductService;
import shop.main.data.service.ReviewService;
import shop.main.utils.URLUtils;

@Controller
public class AjaxFrontController implements ResourceLoaderAware
{
	private ResourceLoader resourceLoader;
	
	 @Autowired
	    ServletContext context;

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}	
	
	@Autowired
	 private ProductService productService;
	
	@Autowired
	 private ReviewService reviewService;
	
	@RequestMapping(value = "/createReview",method = RequestMethod.POST/*, consumes = {"application/json;charset=utf-8"}*/)
	public @ResponseBody String getTranslitURL(Review review) {
		System.out.println("*");
		System.out.println("request is "+review.toString());
		System.out.println("*");	
		String errorMessage="";
		if(review.getUserName()==null || review.getUserName().equals("")){
			errorMessage+="User name is required! ";
		}
		if(review.getUserEmail()==null || review.getUserEmail().equals("")
				|| review.getUserEmail().indexOf("@")<0){
			errorMessage+="Valid email is required!";
		}
		if(errorMessage.equals("")){
			reviewService.save(review);
			productService.updateRating(review.getProduct().getId());
			return "SUCCESS";
		}else{
			return errorMessage;
		}
	}
	
			
	 
}
