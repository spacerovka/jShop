package shop.main.data.objects;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "SKU", nullable = false, length = 50)
	private String SKU;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "url", nullable = true, length = 100, unique = true)
	private String url;
	
	@Column(name = "instock", nullable = true)
	private int instock;

	@Column(name = "price", nullable = false, precision = 12, scale = 2)
	private BigDecimal price;

	@Column(name = "cartDesc", nullable = true, length = 250)
	private String cartDesc;

	@Column(name = "shortDesc", nullable = true, length = 1000)
	private String shortDesc;

	@Column(name = "longDesc", nullable = true)
	private String longDesc;

	@Column(name = "thumb", nullable = true, length = 100)
	private String thumb;

	@Column(name = "image", nullable = true, length = 100)
	private String image;	

	@Column(name = "meta_title", nullable = true)
	private String metaTitle;
	
	@Column(name = "meta_description", nullable = true)
	private String metaDescription;
	
	@Column(name = "status", nullable = false)
	private boolean status;
	
	@Column(name = "featured", nullable = false)
	private boolean featured;

	@Column(name = "created", nullable = true)
	private Date created;

	@Column(name = "edited", nullable = true)
	private Date edited;

	@Column(name = "location", nullable = true, length = 250)
	private String location;	
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)	
	@JoinColumn(columnDefinition="integer", name="category_id", nullable=true)
	private Category category;

//	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//	private List<ProductOption> productOptions;

	@PrePersist
	  protected void onCreate() {
	    created = new Date();
	  }

	  @PreUpdate
	  protected void onUpdate() {
		edited = new Date();
	  }
	
	public Product(String sKU, String name, BigDecimal price, String url) {
		super();
		SKU = sKU;
		this.name = name;
		this.price = price;
		this.url = url;
	}
	
	public Product() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String SKU) {
		this.SKU = SKU;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getinstock() {
		return instock;
	}

	public void setinstock(int instock) {
		this.instock = instock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
	public String getCartDesc() {
		return cartDesc;
	}

	public void setCartDesc(String cartDesc) {
		this.cartDesc = cartDesc;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

//	public List<ProductOption> getProductOptions() {
//		return productOptions;
//	}
//
//	public void setProductOptions(List<ProductOption> productOptions) {
//		this.productOptions = productOptions;
//	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getEdited() {
		return edited;
	}

	public void setEdited(Date edited) {
		this.edited = edited;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isNew() {
		return (this.id == null);
	}
	
	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public int getInstock() {
		return instock;
	}

	public void setInstock(int instock) {
		this.instock = instock;
	}
	
	

}
