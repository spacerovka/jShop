package shop.main.data.mongo;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "orders")
public class Order {

	@Id
	private String orderId;
	private int number;
	private BigDecimal summ;
	@Field("sub")
	private Map<String, OrderProduct> product_list;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BigDecimal getSumm() {
		return summ;
	}

	public void setSumm(BigDecimal summ) {
		this.summ = summ;
	}

	public Map<String, OrderProduct> getProduct_list() {
		return product_list;
	}

	public void setProduct_list(Map<String, OrderProduct> product_list) {
		this.product_list = product_list;
	}

	@Override
	public String toString() {
		StringBuilder products = new StringBuilder("[");
		for(Map.Entry<String, OrderProduct> product : product_list.entrySet()) {
			products.append("<"+product.getValue().toString()+">");
		}
		products.append("]");
		return "Order [orderId=" + orderId + ", number=" + number + ", summ=" + summ + ", product_list=" + products
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((summ == null) ? 0 : summ.hashCode());
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
		Order other = (Order) obj;
		if (number != other.number)
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (summ == null) {
			if (other.summ != null)
				return false;
		} else if (!summ.equals(other.summ))
			return false;
		return true;
	}

	

}
