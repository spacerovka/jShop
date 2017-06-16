package shop.main.data.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotEmpty;

import shop.main.auth.FormValidationGroup;
import shop.main.validation.ValidEmail;
import shop.main.validation.ValidPassword;

@Entity
@Table(name = "user")
public class User {

	@Column(name = "id")
	private Long id;

	@NotNull(groups = { Default.class, FormValidationGroup.class })
	@NotEmpty(groups = { Default.class, FormValidationGroup.class })
	@ValidEmail(groups = { Default.class, FormValidationGroup.class })
	@Column(name = "email", nullable = true, length = 500, unique = true)
	private String email;

	@Id
	@NotNull(groups = { Default.class, FormValidationGroup.class })
	@NotEmpty(groups = { Default.class, FormValidationGroup.class })
	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String userName;

	@NotNull(groups = { Default.class, FormValidationGroup.class })
	@NotEmpty(groups = { Default.class, FormValidationGroup.class })
	@ValidPassword(groups = FormValidationGroup.class)
	@Column(name = "password", nullable = false, length = 60)
	private String password;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserRole> userRole;

	@Column(name = "firstName", nullable = true, length = 50)
	private String firstName;

	@Column(name = "lastName", nullable = true, length = 50)
	private String lastName;

	@Column(name = "country", nullable = true, length = 90)
	private String country;

	@Column(name = "city", nullable = true, length = 90)
	private String city;

	@Column(name = "state", nullable = true, length = 50)
	private String state;

	@Column(name = "zip", nullable = true, length = 20)
	private String zip;

	@Column(name = "emailVerified", nullable = false)
	private boolean emailVerified;

	@Column(name = "registerDate", nullable = true)
	private LocalDate registerDate;

	@Column(name = "verification_code", nullable = true, length = 20)
	private String verification_code;

	@Column(name = "ip", nullable = true, length = 50)
	private String ip;

	@Column(name = "phone", nullable = true, length = 20)
	private String phone;

	@Column(name = "address", nullable = true, length = 100)
	private String address;

	@PrePersist
	protected void onCreate() {

		registerDate = LocalDate.now();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public String getVerification_code() {
		return verification_code;
	}

	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
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
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userRole == null) {
			if (other.userRole != null)
				return false;
		} else if (!userRole.equals(other.userRole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", userName=" + userName + ", password=" + password
				+ ", enabled=" + enabled + ", userRole=" + userRole + ", firstName=" + firstName + ", lastName="
				+ lastName + ", country=" + country + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", emailVerified=" + emailVerified + ", registerDate=" + registerDate + ", verification_code="
				+ verification_code + ", ip=" + ip + ", phone=" + phone + ", address=" + address + "]";
	}

	public boolean isNew() {
		return (this.id == null);
	}

}
