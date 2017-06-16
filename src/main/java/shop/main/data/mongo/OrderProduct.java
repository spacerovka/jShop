package shop.main.data.mongo;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import shop.main.data.entity.Product;

@Document
public class OrderProduct {

	@Id
	private Long productId;
	private BigDecimal price;
	private String product_name;
	private int product_quantity;
	private BigDecimal subTotal;	
	private String product_SKU;
	private String cartDesc;
	private String thumb;
	
	public OrderProduct() {
		this.product_quantity = 1;
	}

	public OrderProduct(String name, BigDecimal price, int quantity, String product_SKU) {
		this.price = price;
		this.product_name = name;
		this.product_quantity = quantity;
		this.product_SKU = product_SKU;
	}
	
	public OrderProduct(Product product) {
		this.productId = product.getId();
		this.product_quantity = 1;
		this.price = product.getPrice();
		this.product_SKU = product.getSku();
		this.product_name = product.getName();
		this.cartDesc = product.getShortDesc();
		this.thumb=product.getImage();
		
	}
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}	

	public String getProduct_SKU() {
		return product_SKU;
	}

	public void setProduct_SKU(String product_SKU) {
		this.product_SKU = product_SKU;
	}

	
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((product_SKU == null) ? 0 : product_SKU.hashCode());
		result = prime * result + ((product_name == null) ? 0 : product_name.hashCode());
		
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
		OrderProduct other = (OrderProduct) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (product_SKU == null) {
			if (other.product_SKU != null)
				return false;
		} else if (!product_SKU.equals(other.product_SKU))
			return false;
		if (product_name == null) {
			if (other.product_name != null)
				return false;
		} else if (!product_name.equals(other.product_name))
			return false;
		
		return true;
	}


	public String getCartDesc() {
		return cartDesc;
	}

	public void setCartDesc(String cartDesc) {
		this.cartDesc = cartDesc;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	@Override
	public String toString() {
		return "OrderProduct [productId=" + productId + ", price=" + price + ", product_name=" + product_name
				+ ", product_quantity=" + product_quantity + ", product_SKU=" + product_SKU + ", cartDesc=" + cartDesc
				+ ", thumb=" + thumb + "]";
	}

}
