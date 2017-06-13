package shop.main.data.mongo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import shop.main.data.entity.Product;

@Document(collection = "orders")
public class Order {

	@Id
	private String orderId;
	private Long user_id;
	private String number;
	private BigDecimal sum;
	private String userName;
	private String shipName;
	private String shipAddress;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	private String email;
	private String managerComment;
	
	private BigDecimal shippingCost;
	private Date date;
	private Boolean shipped;
	private Boolean confirmed;
	
	private String trackNumber;
	
	
	@Field("sub")
	private Map<String, OrderProduct> product_list;
	
	public Order(){
		product_list = new HashMap<String, OrderProduct>();
		sum=new BigDecimal(0.00);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getSum() {
		
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public Map<String, OrderProduct> getProduct_list() {
		return product_list;
	}

	public void setProduct_list(Map<String, OrderProduct> product_list) {
		this.product_list = product_list;
	}
	
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getShipped() {
		return shipped;
	}

	public void setShipped(Boolean shipped) {
		this.shipped = shipped;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	
	
	@Override
	public String toString() {
		
		StringBuilder products = new StringBuilder("[");
		for(Map.Entry<String, OrderProduct> product : product_list.entrySet()) {
			products.append("<"+product.getValue().toString()+">");
		}
		
		return "Order [orderId=" + orderId + ", user_id=" + user_id + ", userName=" + userName + ", sum=" + sum + ", number=" + number
				+ ", shipName=" + shipName + ", shipAddress=" + shipAddress + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + ", country=" + country + ", phone=" + phone + ", email=" + email + ", shippingCost="
				+ shippingCost + ", date=" + date + ", shipped=" + shipped + ", confirmed=" + confirmed
				+ ", trackNumber=" + trackNumber + ", comment "+managerComment+", product_list=" + products + "]";
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((confirmed == null) ? 0 : confirmed.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((managerComment == null) ? 0 : managerComment.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((product_list == null) ? 0 : product_list.hashCode());
		result = prime * result + ((shipAddress == null) ? 0 : shipAddress.hashCode());
		result = prime * result + ((shipName == null) ? 0 : shipName.hashCode());
		result = prime * result + ((shipped == null) ? 0 : shipped.hashCode());
		result = prime * result + ((shippingCost == null) ? 0 : shippingCost.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
		result = prime * result + ((trackNumber == null) ? 0 : trackNumber.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (confirmed == null) {
			if (other.confirmed != null)
				return false;
		} else if (!confirmed.equals(other.confirmed))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (managerComment == null) {
			if (other.managerComment != null)
				return false;
		} else if (!managerComment.equals(other.managerComment))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (product_list == null) {
			if (other.product_list != null)
				return false;
		} else if (!product_list.equals(other.product_list))
			return false;
		if (shipAddress == null) {
			if (other.shipAddress != null)
				return false;
		} else if (!shipAddress.equals(other.shipAddress))
			return false;
		if (shipName == null) {
			if (other.shipName != null)
				return false;
		} else if (!shipName.equals(other.shipName))
			return false;
		if (shipped == null) {
			if (other.shipped != null)
				return false;
		} else if (!shipped.equals(other.shipped))
			return false;
		if (shippingCost == null) {
			if (other.shippingCost != null)
				return false;
		} else if (!shippingCost.equals(other.shippingCost))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		if (trackNumber == null) {
			if (other.trackNumber != null)
				return false;
		} else if (!trackNumber.equals(other.trackNumber))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}
	
	public int getItemCount(){
		int sum = 0;
		for(OrderProduct product: product_list.values()){
			sum+= product.getProduct_quantity();
		}
		return sum;
	}
	
	public void addItem(Product product){
		if (product_list.containsKey(product.getSku())) {
		       product_list.get(product.getSku()).setProduct_quantity(product_list.get(product.getSku()).getProduct_quantity()+1);;
		    } else {
		    	product_list.put(product.getSku(), new OrderProduct(product));
		    }
		updateSum();
	}
	
	public void addQuantity(String sku){
		if (product_list.containsKey(sku)) {
			product_list.get(sku).setProduct_quantity(product_list.get(sku).getProduct_quantity()+1);	    
		}
		updateSum();
	}
	
	public void removeQuantity(String sku){
		if (product_list.containsKey(sku)) {
			
			product_list.get(sku).setProduct_quantity(product_list.get(sku).getProduct_quantity()-1);	    
			if (product_list.get(sku).getProduct_quantity()==0){
				product_list.remove(sku);
			}
		}
		
		updateSum();
	}
	
	private void updateSum() {
		for(OrderProduct product: product_list.values()){
			product.setSubTotal(product.getPrice().multiply(new BigDecimal(product.getProduct_quantity())));
	        sum = sum.add(product.getSubTotal());
		}
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
		
}
