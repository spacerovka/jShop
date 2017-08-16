package ServiceTests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.Block;
import shop.main.data.service.BlockService;
import shop.main.utils.Constants;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:blocks.sql" })
public class TestBlockService {
	private static final String URL = "dolls";
	private final String SEARCH_URL = "do";
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private Pageable pageable;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestBlockService.class);

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Autowired
	@Qualifier("blockService")
	private BlockService blockService;

	@Test
	public void save() {
		LOGGER.info("save");
		Block toSave = new Block();
		toSave.setBlockURL(DUMMY_STRING);
		toSave.setPosition(Constants.BlockType.BOTTOM.name());
		blockService.save(toSave);
		Block found = blockService.findById(toSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void getAllCount() {
		LOGGER.info("getAllCount");
		long result = blockService.getAllCount();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(7, result);
	}

	@Test
	public void listAll() {
		LOGGER.info("listAll");
		List<Block> result = blockService.listAll(pageable);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(7, result.size());
	}

	@Test
	public void findById() {
		LOGGER.info("findById");
		Block result = blockService.findById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(result);
	}

	@Test
	public void deleteById() {
		LOGGER.info("deleteById");
		blockService.deleteById(1);
		Block result = blockService.findById(1);
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(result);
	}

	@Test
	public void findByPositionAndBlockURL() {
		LOGGER.info("findByPositionAndBlockURL");
		Block toSave = new Block();
		toSave.setBlockURL(DUMMY_STRING);
		toSave.setStatus(true);
		toSave.setPosition(Constants.BlockType.BOTTOM.name());
		blockService.save(toSave);
		ArrayList<Block> result = blockService.findByPositionAndBlockURL(Constants.BlockType.BOTTOM.name(),
				DUMMY_STRING);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result.size());
	}
}
