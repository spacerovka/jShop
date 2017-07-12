package shop.main.data.wrapper;

import shop.main.data.entity.User;
import shop.main.data.mongo.Order;

public class OrderUserWrapper {
	private User user;
	private Order order;
	private boolean createUser;

	private String firstName;
	private String lastName;

	private String shipAddress;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	private String email;
	private String username;

	public OrderUserWrapper() {
		order = new Order();
		user = new User();
	}

	public OrderUserWrapper(Order currentOrder, User user2) {
		user = user2;
		order = currentOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.getOrder().setUsername(user.getUsername());
		this.getOrder().setFullName(user.getFirstName() + " " + user.getLastName());
		this.getOrder().setCountry(user.getCountry());
		this.getOrder().setState(user.getState());
		this.getOrder().setCity(user.getCity());
		this.getOrder().setShipAddress(user.getAddress());
		this.getOrder().setZip(user.getZip());
		this.getOrder().setPhone(user.getPhone());
		this.getOrder().setEmail(user.getEmail());
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean isCreateUser() {
		return createUser;
	}

	public void setCreateUser(boolean createUser) {
		this.createUser = createUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		user.setFirstName(firstName);
		order.setFullName(this.firstName + " " + this.lastName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		user.setLastName(lastName);
		order.setFullName(this.firstName + " " + this.lastName);
	}

	public String getShipAddress() {

		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
		user.setAddress(shipAddress);
		order.setShipAddress(shipAddress);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		user.setCity(city);
		order.setCity(city);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		user.setState(state);
		order.setState(state);
	}

	public String getZip() {
		return zip;

	}

	public void setZip(String zip) {
		this.zip = zip;
		user.setZip(zip);
		order.setZip(zip);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
		user.setCountry(country);
		order.setCountry(country);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		user.setPhone(phone);
		order.setPhone(phone);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		user.setEmail(email);
		order.setEmail(email);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		order.setUsername(username);
		user.setUsername(username);
	}

}
