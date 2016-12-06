package shop.main.data.objects;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "email", nullable = false, length=500)
	private String email;
	
	@Column(name="username", nullable=false)
	private String userName;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="enabled", nullable=false)
	private Boolean enabled;
		
	@Column(name = "firstName", nullable = false, length=50)
	private String firstName;
	
	@Column(name = "lastName", nullable = false, length=50)
	private String lastName;
	
	@Column(name = "city", nullable = true, length=90)
	private String city;
	
	@Column(name = "state", nullable = true, length=50)
	private String state;
	
	@Column(name = "zip", nullable = true, length=20)
	private String zip;
	
	@Column(name = "emailVerified", nullable = false)
	private boolean emailVerified;
	
	@Column(name = "registerDate", nullable = false, length=50)
	private Date registerDate;
	
	@Column(name = "verification_code", nullable = false, length=20)
	private String verification_code;
	
	@Column(name = "ip", nullable = true, length=50)
	private String ip;
	
	@Column(name = "phone", nullable = false, length=20)
	private String phone;
	
	@Column(name = "address", nullable = false, length=100)
	private String address;
}
