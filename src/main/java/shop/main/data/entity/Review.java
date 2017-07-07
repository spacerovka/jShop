package shop.main.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "username", nullable = false, length = 100)
	private String userName;

	@NotBlank
	@Column(name = "useremail", nullable = false, length = 100)
	private String userEmail;

	@NotBlank
	@Column(name = "comment", nullable = false, length = 1000)
	private String comment;

	@NotBlank
	@Column(name = "rating", nullable = true)
	private int rating;

	@ManyToOne
	@JoinColumn(name = "product", nullable = false)
	private Product product;

	@Column(name = "created", nullable = true)
	private Date created;

	@PrePersist
	protected void onCreate() {
		if (created == null) {
			created = new Date();
		}
	}

	public Review() {
		rating = 5;
		created = new Date();
	}

	public Review(String userName, String userEmail, String comment, int rating, Product product) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.comment = comment;
		this.rating = rating;
		this.product = product;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	@Override
	public String toString() {
		return "Review [id=" + id + ", userName=" + userName + ", userEmail=" + userEmail + ", comment=" + comment
				+ ", rating=" + rating + ", product=" + product + ", created=" + created + "]";
	}

}
