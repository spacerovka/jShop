package ServiceTests;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.Category;
import shop.main.data.service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfig.class})
@WebAppConfiguration
public class TestCategoryService {
	private final String NAME = "tr";
	private final String URL = "e";
	
	@Autowired
	@Qualifier("categoryService")
	 private CategoryService categoryService;
	
	
	@Test
	public void findByNameAndURL() {
	
	List<Category> results = categoryService.findByNameAndURL(NAME, URL);
//	Assert.assertEquals(products.get(0).getName(), PRODUCT_NAME);
	
	results.stream().forEach(c -> System.out.println(c.getCategoryName()+" "+c.getCategoryURL()));
	System.out.println("*");
	System.out.println("*****************found results "+results.size());
	System.out.println("*");
	}
}
