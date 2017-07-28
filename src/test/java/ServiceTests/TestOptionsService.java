package ServiceTests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.Option;
import shop.main.data.entity.OptionGroup;
import shop.main.data.service.OptionGroupService;
import shop.main.data.service.OptionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
public class TestOptionsService {
	private final String NAME = "a";
	private final String G_NAME = "co";

	@Autowired
	@Qualifier("optionService")
	private OptionService optionService;

	@Autowired
	@Qualifier("optionGroupService")
	private OptionGroupService optionGroupService;

	@Test
	public void findOptionByName() {
		Pageable pageable = new PageRequest(0, 2);
		List<Option> result = optionService.findAllByName(NAME, pageable);

		result.stream().forEach(p -> System.out.println(p.getOptionName()));
		System.out.println("*");
		System.out.println("*****************found  " + result.size());
		System.out.println("*");
	}

	@Test
	public void findOptionGroupByName() {
		Pageable pageable = new PageRequest(0, 2);
		List<OptionGroup> result = optionGroupService.findAllByName(G_NAME, pageable);

		result.stream().forEach(p -> System.out.println(p.getOptionGroupName()));
		System.out.println("*");
		System.out.println("*****************found  " + result.size());
		System.out.println("*");
	}
}
