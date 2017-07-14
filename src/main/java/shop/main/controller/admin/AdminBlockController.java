package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.Block;
import shop.main.data.service.BlockService;
import shop.main.utils.Constants;

@Controller
@RequestMapping(value = { ADMIN_PREFIX })
public class AdminBlockController extends AdminController {

	@Autowired
	private BlockService service;

	/************* Blocks ***/
	@RequestMapping(value = "blocks")
	public String blockList(Model model) {
		int current = 1;
		loadBlockTableData(current, PAGE_SIZE, model);

		return "../admin/blocks/blocks";
	}

	@RequestMapping(value = "ajax/blocks", method = RequestMethod.POST)
	public String blockListPageable(@RequestParam(value = "current", required = false) Integer current,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, Model model) {

		loadBlockTableData(current, pageSize, model);
		return "../admin/blocks/_table";
	}

	private void loadBlockTableData(Integer current, Integer pageSize, Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);
		model.addAttribute("blockList", service.listAll(pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, service.getAllCount());
	}

	@RequestMapping(value = "block", method = RequestMethod.POST)
	public String saveBlock(@ModelAttribute("block") @Valid Block block, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/blocks/edit_block";
		} else {
			if (block.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}

			service.save(block);
			return "redirect:" + ADMIN_PREFIX + "blocks";
		}

	}

	@RequestMapping(value = "block/add", method = RequestMethod.GET)
	public String addBlock(Model model) {
		// TODO add menuTypeList
		model.addAttribute("block", new Block());
		model.addAttribute("blockTypeList", getBlockTypes());
		return "../admin/blocks/edit_block";
	}

	@RequestMapping(value = "block/{id}/update", method = RequestMethod.GET)
	public String editBlock(@PathVariable("id") long id, Model model) {

		model.addAttribute("block", service.findById(id));
		model.addAttribute("blockTypeList", getBlockTypes());

		return "../admin/blocks/edit_block";
	}

	@RequestMapping(value = "block/{id}/delete", method = RequestMethod.GET)
	public String deleteBlock(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");

		return "redirect:" + ADMIN_PREFIX + "blocks";
	}

	private String[] getBlockTypes() {

		// return Constants.blockTypes;
		return Stream.of(Constants.BlockType.values()).map(Constants.BlockType::name).toArray(String[]::new);

	}
}
