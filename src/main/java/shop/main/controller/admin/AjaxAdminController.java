package shop.main.controller.admin;

import java.io.File;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shop.main.data.entity.Discount;
import shop.main.data.entity.Option;
import shop.main.data.entity.OptionGroup;
import shop.main.data.entity.Product;
import shop.main.data.entity.ProductOption;
import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderRepository;
import shop.main.data.service.CategoryService;
import shop.main.data.service.DiscountService;
import shop.main.data.service.OptionGroupService;
import shop.main.data.service.OptionService;
import shop.main.data.service.ProductService;
import shop.main.utils.Constants;
import shop.main.utils.URLUtils;

@Controller
public class AjaxAdminController extends AdminController implements ResourceLoaderAware {
	private ResourceLoader resourceLoader;

	@Autowired
	ServletContext context;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OptionService optionService;

	@Autowired
	private OptionGroupService optionGroupService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DiscountService discountService;

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	@RequestMapping(value = "/ajax/translit")
	public @ResponseBody String getTranslitURL(@RequestBody String name) {
		System.out.println("request is " + name);
		String result = URLUtils.transliterate(name.substring(6));
		// logic
		return result;
	}

	@RequestMapping(value = "/uploadCategoryFiles", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam String prefix, @RequestParam("files") MultipartFile files[],
			HttpServletRequest request) {
		String result = uploadFiles(prefix, files, false);
		System.out.println(result);
		return "redirect:" + getUrlPrefix(request) + "category/"
				+ prefix.substring(prefix.indexOf("/") + 1, prefix.length() - 1) + "/update";

	}

	@RequestMapping(value = "/uploadProductFiles", method = RequestMethod.POST)
	public String handleProductFileUpload(@RequestParam String prefix, @RequestParam("files") MultipartFile files[],
			HttpServletRequest request) {
		String result = uploadFiles(prefix, files, false);
		System.out.println(result);
		return "redirect:" + getUrlPrefix(request) + "product/"
				+ prefix.substring(prefix.indexOf("/") + 1, prefix.length() - 1) + "/update";
	}

	@RequestMapping(value = "/uploadMainPageImages", method = RequestMethod.POST)
	public String handleMainPageImagesFileUpload(@RequestParam String prefix,
			@RequestParam("files") MultipartFile files[], HttpServletRequest request) {
		String result = uploadFiles(prefix, files, false);
		System.out.println(result);
		return "redirect:" + getUrlPrefix(request) + "mainpage";
	}

	@RequestMapping(value = "/uploadProductMainImage", method = RequestMethod.POST)
	public String handleProductMainImageUpload(@RequestParam String prefix,
			@RequestParam("files") MultipartFile files[], HttpServletRequest request) {
		String folder = context.getRealPath("/") + "/resources/uploads/" + prefix;
		try {
			File theDir = new File(folder);
			theDir.mkdir();
		} catch (SecurityException se) {
		}
		String result = uploadFiles(prefix + "main/", files, true);
		System.out.println(result);
		return "redirect:" + getUrlPrefix(request) + "product/"
				+ prefix.substring(prefix.indexOf("/") + 1, prefix.length() - 1) + "/update";
	}

	@RequestMapping(value = "/ajax/images", method = RequestMethod.POST)
	@ResponseBody
	public String handleTinyMCEUpload(@RequestParam("files") MultipartFile files[]) {
		System.out.println("uploading______________________________________MultipartFile " + files.length);
		Random r = new Random();
		int Low = 10;
		int High = 100;
		String prefix = "tinyMCE/" + String.valueOf(r.nextInt(High - Low) + Low);
		String filePath = "/resources/uploads/" + prefix + files[0].getOriginalFilename();
		String result = uploadFilesFromTinyMCE("prefix", files, false);
		System.out.println(result);
		return "{\"location\":\"" + filePath + "\"}";

	}

	private String uploadFiles(String prefix, MultipartFile files[], boolean isMain) {
		System.out.println("uploading______________________________________" + prefix);
		try {
			String folder = context.getRealPath("/") + "/resources/uploads/" + prefix;
			StringBuffer result = new StringBuffer();
			byte[] bytes = null;
			result.append("Uploading of File(s) ");

			for (int i = 0; i < files.length; i++) {
				if (!files[i].isEmpty()) {

					try {
						boolean created = false;

						try {
							File theDir = new File(folder);
							theDir.mkdir();
							created = true;
						} catch (SecurityException se) {
							// handle it
						}
						if (created) {
							System.out.println("DIR created");
						}
						int count = new File(folder).listFiles().length;
						System.out.println("count " + count);
						// create folder if not exists - products and
						// categories, create id folder, count files in folder
						String path = "";
						if (isMain) {
							path = folder + "main" + files[i].getOriginalFilename()
									.substring(files[i].getOriginalFilename().lastIndexOf('.'));
						} else {
							path = folder + count + files[i].getOriginalFilename()
									.substring(files[i].getOriginalFilename().lastIndexOf('.'));
						}
						File destination = new File(path);// files[i].getOriginalFilename()
						System.out.println("--> " + destination);
						files[i].transferTo(destination);

					} catch (Exception e) {
						throw new RuntimeException("Product Image saving failed", e);
					}

				} else
					result.append(files[i].getOriginalFilename() + " Failed. ");

			}
			File[] listOfFiles = new File(folder).listFiles();

			for (int f = 0; f < listOfFiles.length; f++) {
				if (listOfFiles[f].isFile()) {
					result.append(listOfFiles[f].getName() + " Ok. ");
				}
			}
			return result.toString();

		} catch (Exception e) {
			return "Error Occured while uploading files." + " => " + e.getMessage();
		}
	}

	private String uploadFilesFromTinyMCE(String prefix, MultipartFile files[], boolean isMain) {
		System.out.println("uploading______________________________________" + prefix);
		try {
			String folder = context.getRealPath("/") + "/resources/uploads/" + prefix;
			StringBuffer result = new StringBuffer();
			byte[] bytes = null;
			result.append("Uploading of File(s) ");

			for (int i = 0; i < files.length; i++) {
				if (!files[i].isEmpty()) {

					try {
						boolean created = false;

						try {
							File theDir = new File(folder);
							theDir.mkdir();
							created = true;
						} catch (SecurityException se) {
							se.printStackTrace();
						}
						if (created) {
							System.out.println("DIR created");
						}
						String path = "";
						path = folder + files[i].getOriginalFilename();
						File destination = new File(path);
						System.out.println("--> " + destination);
						files[i].transferTo(destination);
						result.append(files[i].getOriginalFilename() + " Succsess. ");
					} catch (Exception e) {
						throw new RuntimeException("Product Image saving failed", e);
					}

				} else
					result.append(files[i].getOriginalFilename() + " Failed. ");

			}

			return result.toString();

		} catch (Exception e) {
			return "Error Occured while uploading files." + " => " + e.getMessage();
		}
	}

	@RequestMapping(value = "ajax/addProductOption", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
	public String addProductOption(Product product, HttpServletRequest request, Model model) {
		for (ProductOption po : product.getProductOptions()) {
			if (po.getOption().getId() == -1) {
				po.setOption(null);
			}
			if (po.getOptionGroup().getId() == -1) {
				po.setOptionGroup(null);
			} else {
				po.setOptionGroup(optionGroupService.fingOptionById(po.getOptionGroup().getId()));
			}
		}
		ProductOption pOption = new ProductOption();
		pOption.setProduct(product);
		pOption.setOption(new Option());
		pOption.setOptionGroup(new OptionGroup());
		product.getProductOptions().add(pOption);
		model.addAttribute("product", product);
		model.addAttribute("parentCategoryList", categoryService.listAll());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/_edit_product_form";
	}

	@RequestMapping(value = "ajax/updateProductOption", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
	public String updateProductOption(Product product, HttpServletRequest request, Model model) {
		for (ProductOption po : product.getProductOptions()) {
			if (po.getOption().getId() == -1) {
				po.setOption(null);
			}
			if (po.getOptionGroup().getId() == -1) {
				po.setOptionGroup(null);
			} else {
				po.setOptionGroup(optionGroupService.fingOptionById(po.getOptionGroup().getId()));
			}
		}
		model.addAttribute("product", product);
		model.addAttribute("parentCategoryList", categoryService.listAll());
		model.addAttribute("optiongroupList", optionGroupService.listAll());
		return "../admin/_edit_product_form";
	}

	@RequestMapping(value = "/ajax/addProductToOrder", method = RequestMethod.GET)
	public String addProductToOrder(@RequestParam String id, @RequestParam String orderid, Model model) {
		Order order = orderRepository.findOne(orderid);
		Product product = productService.fingProductById(Long.parseLong(id));
		order.addItem(product);
		orderRepository.save(order);
		model.addAttribute("order", order);
		model.addAttribute("countryList", Constants.getCountryList());

		return "../admin/orders/_order";
	}

	@RequestMapping(value = "/ajax/addQuantity", method = RequestMethod.POST)
	public String addQuantity(@RequestParam String sku, @RequestParam String orderid, HttpServletRequest request,
			Model model) {
		Order order = orderRepository.findOne(orderid);
		order.addQuantity(sku);
		orderRepository.save(order);
		model.addAttribute("order", order);
		return "../admin/orders/_order";
	}

	@RequestMapping(value = "/ajax/removeQuantity", method = RequestMethod.POST)
	public String removeQuantity(@RequestParam String sku, @RequestParam String orderid, HttpServletRequest request,
			Model model) {
		Order order = orderRepository.findOne(orderid);
		order.removeQuantity(sku);
		orderRepository.save(order);
		model.addAttribute("order", order);
		return "../admin/orders/_order";
	}

	@RequestMapping(value = "/ajax/addCoupon", method = RequestMethod.POST)
	public String addCoupon(@RequestParam String code, @RequestParam String orderid, HttpServletRequest request,
			Model model) {
		Order order = orderRepository.findOne(orderid);
		if (order.getDiscount() <= 0) {
			Discount discount = discountService.findByCoupon(code);
			if (discount != null) {
				order.addCoupon(discount.getDiscount(), discount.getSalename());
				orderRepository.save(order);
			} else {
				model.addAttribute("couponError", "Discount not found - " + order.getDiscountName());
			}
		} else {
			model.addAttribute("couponError", "This order already has a discount - " + order.getDiscountName());
		}
		model.addAttribute("order", order);
		return "../admin/orders/_order";
	}

}
