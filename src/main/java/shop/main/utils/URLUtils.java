package shop.main.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

public class URLUtils {
	
	
	public static String transliterate(String message){
		message = message.trim();
		char[] abcCyr =   {'%', '№','&', '#', '"', ' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Б','Э','Ю','Я','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		String[] abcLat = {"percent", "number","and", "", "", "_","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch", "","i", "","e","ju","ja","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch", "","i", "","e","ju","ja","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
		for(int x = 0; x < abcCyr.length; x++ )
		if (message.charAt(i) == abcCyr[x]) {
		builder.append(abcLat[x]);
		}
		}
		return builder.toString().replaceAll("[^_a-zA-Z0-9]|", "").replaceAll("_$", "");

		}
	
	public static List<String> getCategoryImages(ServletContext context,Long id){
		String folder = context.getRealPath("/")+ "/resources/uploads/categories/"+id+"/";
		return findImages(folder);		
		
	}
	
	public static List<String> getProductImages(ServletContext context,Long id){
		String folder = context.getRealPath("/")+ "/resources/uploads/products/"+id+"/";
		return findImages(folder);		
		
	}
	
	public static List<String> findImages(String folder){
		List<String> images = new ArrayList<String>();
		
		File[] listOfFiles = new File(folder).listFiles();
		if(listOfFiles!=null){
		    for (File f : listOfFiles) {	      
		    	  images.add(f.getName()) ;		      
		    }
		}
		return images;
	}
}
