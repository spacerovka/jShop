package shop.main.data.entity;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "sku", unique = true, nullable = false, length = 50)
	private String sku;

	@NotBlank
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@NotBlank
	@Column(name = "url", nullable = true, length = 100, unique = true)
	private String url;

	@Column(name = "instock", nullable = true)
	private int instock;

	@NotNull
	@Min(value = 0, message = "Price can not be less then 0")
	@Column(name = "price", nullable = false, precision = 12, scale = 2)
	private BigDecimal price;

	@Column(name = "cartDesc", nullable = true, length = 250)
	private String cartDesc;

	@Column(name = "shortDesc", nullable = true, length = 1000)
	private String shortDesc;

	@Column(name = "longDesc", nullable = true, columnDefinition = "TEXT")
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
	@JoinColumn(columnDefinition = "integer", name = "category", nullable = true)
	private Category category;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProductOption> productOptions;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Review> reviews;

	@Column(name = "rating", columnDefinition = "int default 0")
	private long rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "size", nullable = true)
	private ParcelSize size;

	@PrePersist
	protected void onCreate() {
		created = new Date();

	}

	@PreUpdate
	protected void onUpdate() {
		edited = new Date();
	}

	public Product(String sku, String name, BigDecimal price, String url) {
		super();
		this.sku = sku;
		this.name = name;
		this.price = price;
		this.url = url;
		this.rating = 0;
	}

	public Product() {

	}

}
