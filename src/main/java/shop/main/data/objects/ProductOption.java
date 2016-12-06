package shop.main.data.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "productOption")
public class ProductOption {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="option_id", nullable=false)	
	private Option option_id;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="optiongroup_id", nullable=false)	
	private OptionGroup optionGroup;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)	
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Option getOption_id() {
		return option_id;
	}

	public void setOption_id(Option option_id) {
		this.option_id = option_id;
	}

	public OptionGroup getOptiongroup() {
		return optionGroup;
	}

	public void setOptiongroup(OptionGroup optiongroup) {
		this.optionGroup = optiongroup;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProductOption [id=" + id + ", option_id=" + option_id + ", optiongroup=" + optionGroup + ", product="
				+ product + "]";
	}
	
	
	
}
