package shop.main.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import shop.main.validation.ValidEmail;

@Entity
@Table(name = "contact_us_message")
public class ContactUsMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@NotNull
	@NotEmpty
	@Column(name = "username", nullable = false, length = 100)
	private String userName;

	@NotNull
	@NotEmpty
	@ValidEmail
	@Column(name = "useremail", nullable = false, length = 100)
	private String userEmail;

	@NotNull
	@NotEmpty
	@Column(name = "theme", nullable = false, length = 100)
	private String theme;

	@NotNull
	@NotEmpty
	@Column(name = "comment", nullable = false, length = 1000)
	private String comment;

	@Column(name = "watched", nullable = true)
	private boolean watched;

	@Column(name = "created", nullable = true)
	private Date created;

	@PrePersist
	protected void onCreate() {
		if (created == null) {
			created = new Date();
		}
	}

	public ContactUsMessage() {
		this.watched = false;
		created = new Date();
	}

	public ContactUsMessage(String userName, String userEmail, String comment, int rating, Product product) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.comment = comment;
		this.watched = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isNew() {
		return (this.id == null);
	}

	public boolean isWatched() {
		return watched;
	}

	public void setWatched(boolean watched) {
		this.watched = watched;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
