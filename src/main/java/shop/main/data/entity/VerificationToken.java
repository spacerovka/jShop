package shop.main.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "verificationtoken")
public class VerificationToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "token", nullable = false)
	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(name = "expiryDate", nullable = true)
	private LocalDateTime expiryDate;

	public VerificationToken() {

	}

	public VerificationToken(String token, User user) {
		this.user = user;
		this.token = token;
	}

	private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(new Timestamp(cal.getTime().getTime()));
		// cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		// return new Date(cal.getTime().getTime());
		LocalDateTime t = LocalDateTime.now();
		t = t.withMinute(expiryTimeInMinutes);
		return t;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);

	}

}
