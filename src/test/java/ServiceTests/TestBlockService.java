package ServiceTests;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.objects.Block;
import shop.main.data.service.BlockService;
import shop.main.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfig.class})
@WebAppConfiguration
public class TestBlockService {
	private final String POSITION = Constants.BlockType.LEFT_TOP.name();
	private final String PRODUCT_URL = "ra";
	public static final String CONTENT = "test content LEFT_TOP";
	
	@Autowired
	@Qualifier("blockService")
	 private BlockService blockService;
	
	
	@Test
	public void findByNameAndURL() {
	
	List<Block> result = blockService.findByPositionAndBlockURL(POSITION, PRODUCT_URL);
//	Assert.assertEquals(products.get(0).getName(), PRODUCT_NAME);
	
	result.stream().forEach(p -> System.out.println(p.getContent()+" "+p.getBlockURL()));
	System.out.println("*");
	System.out.println("*****************found products "+result.size());
	System.out.println("*");
	Assert.assertEquals(result.get(0).getContent(), CONTENT);
	}
}
