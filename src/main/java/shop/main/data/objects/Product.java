package shop.main.data.objects;

import java.math.BigDecimal;
import java.sql.Date;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "SKU", nullable = false, length=50)
	private String SKU;

	@Column(name = "name", nullable = false, length=100)
	private String name;

	@Column(name = "instock", nullable = true)
	private int instock;

	@Column(name = "price", nullable = false, precision=12, scale=2)
	private BigDecimal price;
	
	@Column(name = "cartDesc", nullable = true, length=250)
	private String cartDesc;
	
	@Column(name = "shortDesc", nullable = true, length=1000)
	private String shortDesc;
	
	@Column(name = "longDesc", nullable = true)
	private String longDesc;
	
	@Column(name = "thumb", nullable = true, length=100)
	private String thumb;
	
	@Column(name = "image", nullable = true, length=100)
	private String image;
	
	@Column(name = "url", nullable = true, length=100, unique=true)
	private String url;
	
	@Column(name = "active", nullable = false)
	private boolean active;
	
	@Column(name = "created", nullable = false)
	private Date created;
	
	@Column(name = "edited", nullable = false)
	private Date edited;
	
	@Column(name = "location", nullable = true, length=250)
	private String location;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false) 
	private Category category;
	 
	@OneToMany(mappedBy="product", fetch=FetchType.LAZY)
	private List<ProductOption> productOptions;
	 

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

	public int getInstock() {
		return instock;
	}

	public void setInstock(int instock) {
		this.instock = instock;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public List<ProductOption> getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(List<ProductOption> productOptions) {
		this.productOptions = productOptions;
	}

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
	

}
