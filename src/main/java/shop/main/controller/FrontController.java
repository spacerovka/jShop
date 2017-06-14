package shop.main.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import shop.main.data.entity.Block;
import shop.main.data.entity.MenuItem;
import shop.main.data.service.BlockService;
import shop.main.data.service.CategoryService;
import shop.main.data.service.MenuItemService;
import shop.main.data.service.ProductOptionService;
import shop.main.data.service.ProductService;
import shop.main.data.service.SitePropertyService;
import shop.main.data.service.StaticPageService;
import shop.main.utils.Constants;

@Controller
public class FrontController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);

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
	protected ServletContext context;

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

}
