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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "categoryName", nullable = false, length=50)
	private String categoryName;
	
	@Column(name = "categoryURL", nullable = false, length=100, unique=true)
	private String categoryURL;
	
	@OneToMany(mappedBy="category", fetch=FetchType.LAZY)
	private List<Product> products;	
		
	@ManyToOne(optional = true, fetch = FetchType.LAZY)	
	@JoinColumn(columnDefinition="integer", name="parent_id", nullable=true)
    private Category parentCategory;
	
	@OneToMany(mappedBy="parentCategory", fetch=FetchType.EAGER)
    private List<Category> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryURL() {
		return categoryURL;
	}

	public void setCategoryURL(String categoryURL) {
		this.categoryURL = categoryURL;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}	
	

	public Category getParent() {
		return parentCategory;
	}

	public void setParent(Category parent) {
		this.parentCategory = parent;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((categoryURL == null) ? 0 : categoryURL.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Category other = (Category) obj;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (categoryURL == null) {
			if (other.categoryURL != null)
				return false;
		} else if (!categoryURL.equals(other.categoryURL))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", categoryURL=" + categoryURL + "]";
	}
	
	
}
