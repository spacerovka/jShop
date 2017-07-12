package shop.main.data.wrapper;

import java.util.ArrayList;
import java.util.List;

import shop.main.data.entity.SiteProperty;

public class SitePropertiesWrapper {
	private List<SiteProperty> propertyList;
	

    public SitePropertiesWrapper(List<SiteProperty> propertyList) {
		super();
		this.propertyList = propertyList;
	}

	public SitePropertiesWrapper() {
         this.propertyList = new ArrayList<SiteProperty>();
    }

    public List<SiteProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<SiteProperty> propertyList) {
        this.propertyList = propertyList;
    }

    public void add(SiteProperty property) {
        this.propertyList.add(property);
    }
}
