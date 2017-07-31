package shop.main.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import shop.main.captcha.CaptchaSettings;
import shop.main.captcha.ReCaptchaService;
import shop.main.data.entity.Block;
import shop.main.data.entity.Category;
import shop.main.data.entity.MenuItem;
import shop.main.data.entity.Product;
import shop.main.data.service.BlockService;
import shop.main.data.service.CategoryService;
import shop.main.data.service.ContactUsMessageService;
import shop.main.data.service.MenuItemService;
import shop.main.data.service.ProductOptionService;
import shop.main.data.service.ProductService;
import shop.main.data.service.SitePropertyService;
import shop.main.data.service.StaticPageService;
import shop.main.data.service.UserService;
import shop.main.data.service.WishListService;
import shop.main.utils.Constants;
import shop.main.utils.URLUtils;

@Controller
public class FrontController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);
	protected static final Integer PAGE_SIZE = 2;

	@Autowired
	@Qualifier("dataSourceMysql")
	protected DataSource dataSourceMysql;

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected ProductService productService;

	@Autowired
	protected CategoryService categoryService;

	@Autowired
	protected ProductOptionService productOptionService;

	@Autowired
	protected SitePropertyService sitePropertyService;

	@Autowired
	protected MenuItemService menuItemService;

	@Autowired
	protected StaticPageService staticPageService;

	@Autowired
	private BlockService blockService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected ServletContext context;

	@Autowired
	protected CaptchaSettings captchaSettings;

	@Autowired
	protected ReCaptchaService reCaptchaService;

	@Autowired
	protected WishListService wishListService;

	@Autowired
	protected ContactUsMessageService contactService;

	@ModelAttribute("SITE_NAME")
	public String getSiteName() {
		return this.sitePropertyService.findOneByName(Constants.SITE_NAME).getContent();
	}

	@ModelAttribute("MENU_LEFT")
	public List<MenuItem> getLeftMenu() {
		return this.menuItemService.findLeftMenu();
	}

	@ModelAttribute("MENU_RIGHT")
	public List<MenuItem> getRightMenu() {
		return this.menuItemService.findRightMenu();
	}

	@ModelAttribute("TOP_BLOCKS")
	public List<Block> getTopBlocks(HttpServletRequest request) {
		return this.blockService.findByPositionAndBlockURL(Constants.BlockType.TOP.name(), request.getServletPath());
	}

	@ModelAttribute("LEFT_TOP_BLOCKS")
	public List<Block> getTopLeftBlocks(HttpServletRequest request) {
		return this.blockService.findByPositionAndBlockURL(Constants.BlockType.LEFT_TOP.name(),
				request.getServletPath());
	}

	@ModelAttribute("LEFT_BOTTOM_BLOCKS")
	public List<Block> getBottomLeftBlocks(HttpServletRequest request) {
		return this.blockService.findByPositionAndBlockURL(Constants.BlockType.LEFT_BOTTOM.name(),
				request.getServletPath());
	}

	@ModelAttribute("FOOTER_COL_LEFT_BLOCKS")
	public List<Block> getFooterColumnLeftBlocks(HttpServletRequest request) {
		return this.blockService.findByPositionAndBlockURL(Constants.BlockType.FOOTER_COL_LEFT.name(),
				request.getServletPath());
	}

	@ModelAttribute("FOOTER_COL_CENTER_BLOCKS")
	public List<Block> getVCenterBlocks(HttpServletRequest request) {
		return this.blockService.findByPositionAndBlockURL(Constants.BlockType.FOOTER_COL_CENTER.name(),
				request.getServletPath());
	}

	@ModelAttribute("FOOTER_COL_RIGHT_BLOCKS")
	public List<Block> getFooterColumnRightBlocks(HttpServletRequest request) {
		return this.blockService.findByPositionAndBlockURL(Constants.BlockType.FOOTER_COL_RIGHT.name(),
				request.getServletPath());
	}

	@ModelAttribute("BOTTOM_BLOCKS")
	public List<Block> getBottomBlocks(HttpServletRequest request) {
		return this.blockService.findByPositionAndBlockURL(Constants.BlockType.BOTTOM.name(), request.getServletPath());
	}

	protected void addPaginator(Model model, int current, int pageSize, long count) {
		System.out.println("**************found: " + count);
		List<Integer> pagination = new ArrayList<>(0);
		long pagesCount = 0;
		if (count % pageSize > 0) {
			pagesCount = count / pageSize + 1;
		} else {
			pagesCount = count / pageSize;
		}
		for (int i = 1; i <= pagesCount; i++) {
			pagination.add(i);
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("pagesCount", pagesCount);
		if (pagesCount < 2) {
			model.addAttribute("RENDER_NEXT", false);
			model.addAttribute("RENDER_PREV", false);
		} else {
			if (current == pagesCount) {
				model.addAttribute("RENDER_NEXT", false);
			} else {
				model.addAttribute("RENDER_NEXT", true);
			}
			if (current == 1) {
				model.addAttribute("RENDER_PREV", false);
			} else {
				model.addAttribute("RENDER_PREV", true);
			}
		}
	}

	protected void addOrderPaginator(Model model, int current, int pageSize, long count) {
		System.out.println("**************found: " + count);
		List<Integer> pagination = new ArrayList<>(0);
		long pagesCount = 0;
		if (count % pageSize > 0) {
			pagesCount = count / pageSize + 1;
		} else {
			pagesCount = count / pageSize;
		}
		for (int i = 1; i <= pagesCount; i++) {
			pagination.add(i);
		}
		model.addAttribute("pagination_order", pagination);
		model.addAttribute("pagesCount_order", pagesCount);
		if (pagesCount < 2) {
			model.addAttribute("RENDER_NEXT_order", false);
			model.addAttribute("RENDER_PREV_order", false);
		} else {
			if (current == pagesCount) {
				model.addAttribute("RENDER_NEXT_order", false);
			} else {
				model.addAttribute("RENDER_NEXT_order", true);
			}
			if (current == 1) {
				model.addAttribute("RENDER_PREV_order", false);
			} else {
				model.addAttribute("RENDER_PREV_order", true);
			}
		}
	}

	protected void addProductPaginator(Model model, int current, int pageSize, long count) {
		model.addAttribute("current_product", current);
		model.addAttribute("pageSize_product", pageSize);
		System.out.println("**************found: " + count);
		List<Integer> pagination = new ArrayList<>(0);
		long pagesCount = 0;
		if (count % pageSize > 0) {
			pagesCount = count / pageSize + 1;
		} else {
			pagesCount = count / pageSize;
		}
		for (int i = 1; i <= pagesCount; i++) {
			pagination.add(i);
		}
		model.addAttribute("pagination_product", pagination);
		model.addAttribute("pagesCount_product", pagesCount);
		if (pagesCount < 2) {
			model.addAttribute("RENDER_NEXT_product", false);
			model.addAttribute("RENDER_PREV_product", false);
		} else {
			if (current == pagesCount) {
				model.addAttribute("RENDER_NEXT_product", false);
			} else {
				model.addAttribute("RENDER_NEXT_product", true);
			}
			if (current == 1) {
				model.addAttribute("RENDER_PREV_product", false);
			} else {
				model.addAttribute("RENDER_PREV_product", true);
			}
		}
	}

	public void loadFilterProductTableData(List<Long> filterList, List<Long> childCategories, Integer current,
			Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		List<Product> products = productService.findFilteredProductsInCategory(filterList, childCategories, pageable);
		products.stream().forEach(p -> p.setImage(URLUtils.getProductImage(context, p.getId())));
		model.addAttribute("products", products);
		addProductPaginator(model, current, pageSize,
				productService.countFilteredProductsInCategory(filterList, childCategories));

	}

	protected void addMenuItems(Model model) {
		List<Category> data = categoryService.findAllParentCategories();
		model.addAttribute("menu", data);
	}

	protected void addBreadCrumb(Category category, List<Category> breadcrumbList) {
		breadcrumbList.add(0, category);
		System.out.println("added category " + category.getCategoryName());
		if (category.getParentCategory() != null) {
			addBreadCrumb(category.getParentCategory(), breadcrumbList);
		}
	}

	protected void createChildrenList(Category category, List<Long> childrenList) {
		childrenList.add(category.getId());
		System.out.println("added category " + category.getCategoryName());
		if (!category.getChildren().isEmpty()) {
			category.getChildren().stream().forEach(c -> createChildrenList(c, childrenList));
		}
	}
}
