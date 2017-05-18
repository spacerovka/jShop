package shop.main.controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shop.main.data.mongo.Order;
import shop.main.data.objects.Option;
import shop.main.data.objects.OptionGroup;
import shop.main.data.objects.Product;
import shop.main.data.objects.ProductOption;
import shop.main.data.service.CategoryService;
import shop.main.data.service.OptionGroupService;
import shop.main.data.service.OptionService;
import shop.main.utils.URLUtils;

@Controller
public class AjaxAdminController implements ResourceLoaderAware {
	private ResourceLoader resourceLoader;

	@Autowired
	ServletContext context;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OptionService optionService;

	@Autowired
	private OptionGroupService optionGroupService;

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	@RequestMapping(value = "a/translit")
	public @ResponseBody String getTranslitURL(@RequestBody String name) {
		System.out.println("request is " + name);
		String result = URLUtils.transliterate(name.substring(6));
		// logic
		return result;
	}

	@RequestMapping(value = "/uploadCategoryFiles", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam String prefix, @RequestParam("files") MultipartFile files[]) {
		String result = uploadFiles(prefix, files, false);
		System.out.println(result);
		return "redirect:/a/category/" + prefix.substring(prefix.indexOf("/") + 1, prefix.length() - 1) + "/update";

	}

	@RequestMapping(value = "/uploadProductFiles", method = RequestMethod.POST)
	public String handleProductFileUpload(@RequestParam String prefix, @RequestParam("files") MultipartFile files[]) {
		String result = uploadFiles(prefix, files, false);
		System.out.println(result);
		return "redirect:/a/product/" + prefix.substring(prefix.indexOf("/") + 1, prefix.length() - 1) + "/update";
	}

	@RequestMapping(value = "/uploadMainPageImages", method = RequestMethod.POST)
	public String handleMainPageImagesFileUpload(@RequestParam String prefix,
			@RequestParam("files") MultipartFile files[]) {
		String result = uploadFiles(prefix, files, false);
		System.out.println(result);
		return "redirect:/a/mainpage";
	}

	@RequestMapping(value = "/uploadProductMainImage", method = RequestMethod.POST)
	public String handleProductMainImageUpload(@RequestParam String prefix,
			@RequestParam("files") MultipartFile files[]) {
		String folder = context.getRealPath("/") + "/resources/uploads/" + prefix;
		try {
			File theDir = new File(folder);
			theDir.mkdir();
		} catch (SecurityException se) {
		}
		String result = uploadFiles(prefix + "main/", files, true);
		System.out.println(result);
		return "redirect:/a/product/" + prefix.substring(prefix.indexOf("/") + 1, prefix.length() - 1) + "/update";
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

	@RequestMapping(value = "a/addProductOption", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
	public String addProductOption(Product product, HttpServletRequest request, Model model) {
		for (ProductOption po : product.getProductOptions()) {
			if (po.getOption().getId() == -1) {
				po.setOption(null);
			}
			if (po.getOptionGroup().getId() == -1) {
				po.setOptionGroup(null);
			}else {
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

	@RequestMapping(value = "a/updateProductOption", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
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
}
