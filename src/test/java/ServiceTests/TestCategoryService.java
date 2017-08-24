package ServiceTests;

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
import shop.main.data.entity.Category;
import shop.main.data.service.CategoryService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:delete_test_data.sql", "classpath:categories.sql" })
public class TestCategoryService {
	private static final String NAME = "Dolls";
	private static final String URL = "dolls";
	private final String SEARCH_NAME = "do";
	private final String SEARCH_URL = "do";
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private Pageable pageable;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestCategoryService.class);

	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Test
	public void findByNameAndURLSQLCOUNT() {
		LOGGER.info("findByNameAndURLSQLCOUNT");
		List<Category> results = categoryService.findByNameAndURL(SEARCH_NAME, SEARCH_URL, pageable);
		AssertSqlCount.assertSelectCount(1);
	}

	@Test
	public void findByNameAndURLDataValid() {
		LOGGER.info("findByNameAndURLDataValid");
		List<Category> results = categoryService.findByNameAndURL(SEARCH_NAME, SEARCH_URL, pageable);
		Assert.assertEquals(results.get(0).getCategoryName(), NAME);
		Assert.assertEquals(results.get(0).getCategoryURL(), URL);
	}

	@Test
	public void countByNameAndURL() {
		LOGGER.info("countByNameAndURL");
		long result = categoryService.countByNameAndURL(SEARCH_NAME, SEARCH_URL);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(1, result);
	}

	@Test
	public void saveCategory() {
		Category categoryToSave = new Category();
		categoryToSave.setCategoryName(DUMMY_STRING);
		categoryToSave.setCategoryURL(DUMMY_STRING);
		categoryService.saveCategory(categoryToSave);
		Category found = categoryService.findCategoryById(categoryToSave.getId());
		AssertSqlCount.assertSelectCount(1);
		Assert.assertNotNull(found);
	}

	@Test
	public void findAllParentCategories() {
		LOGGER.info("findAllParentCategories");
		List<Category> results = categoryService.findAllParentCategories();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(2, results.size());
	}

	@Test
	public void listAll() {
		LOGGER.info("listAll");
		List<Category> results = categoryService.listAll();
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(7, results.size());
	}

	@Test
	public void findCategoryByUrl() {
		LOGGER.info("findCategoryByUrl");
		Category found = categoryService.findCategoryByUrl(URL);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(NAME, found.getCategoryName());
	}

	@Test
	public void findCategoryById() {
		LOGGER.info("findCategoryById");
		Category found = categoryService.findCategoryById(1);
		AssertSqlCount.assertSelectCount(1);
		Assert.assertEquals(new Long(1), found.getId());
	}

	@Test
	public void checkUniqueURL() {
		LOGGER.info("checkUniqueURL");
		Category categoryToSave = new Category();
		categoryToSave.setCategoryURL(URL);
		boolean unique = categoryService.checkUniqueURL(categoryToSave);
		Assert.assertFalse(unique);
		AssertSqlCount.assertSelectCount(1);
	}

	@Test
	public void deleteCategoryById() {
		LOGGER.info("deleteCategoryById");
		categoryService.deleteCategoryById(1);
		Category found = categoryService.findCategoryById(1);
		// update child categories and delete this one -> 2 queries
		AssertSqlCount.assertSelectCount(2);
		Assert.assertNull(found);
	}

}
