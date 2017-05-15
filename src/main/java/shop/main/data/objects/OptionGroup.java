package shop.main.data.objects;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "optionGroup")
public class OptionGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "optionGroupName", nullable = false, length=50)
	private String optionGroupName;	
	
	@Column(name = "description", nullable = true, length=50)
	private String description;
	
	@OneToMany(mappedBy="optionGroup", fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Option> options;
	
	@OneToMany(mappedBy="optionGroup", fetch=FetchType.LAZY,cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<ProductOption> productOptions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOptionGroupName() {
		return optionGroupName;
	}

	public void setOptionGroupName(String optionGroupName) {
		this.optionGroupName = optionGroupName;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public List<ProductOption> getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(List<ProductOption> productOptions) {
		this.productOptions = productOptions;
	}	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((optionGroupName == null) ? 0 : optionGroupName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionGroup other = (OptionGroup) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (optionGroupName == null) {
			if (other.optionGroupName != null)
				return false;
		} else if (!optionGroupName.equals(other.optionGroupName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OptionGroup [id=" + id + ", optionGroupName=" + optionGroupName 
				+ ", description=" + description +  "]";
	}

	public boolean isNew() {
		return (this.id == null);
		}
	
}
