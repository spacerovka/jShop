package DAOTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.OptionGroup;
import shop.main.data.service.OptionGroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
public class TestEntities {

	@Autowired
	@Qualifier("optionGroupService")
	private OptionGroupService optionGroupService;

	@Test
	public void testAddOptionGroup() {

		OptionGroup group = new OptionGroup();
		group.setDescription("my descr//");
		group.setOptionGroupName("MyGroup");
		optionGroupService.save(group);
		final long groupId = group.getId();
		OptionGroup insertedgroup = optionGroupService.findOptionById(groupId);
		Assert.assertEquals(insertedgroup.getOptionGroupName(), group.getOptionGroupName());
		// assert if all necessary fields are equal
		// or assert if both are equals in case your Friends class implements
		// equals method
		System.out.println("id is " + groupId);
	}
}
