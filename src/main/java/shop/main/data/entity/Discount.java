package shop.main.data.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "discount")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@NotNull
	@NotEmpty
	@Column(name = "status", nullable = false)
	private boolean status;

	@NotNull
	@NotEmpty
	@Column(name = "salename", nullable = false, length = 250)
	private String salename;

	@NotNull
	@NotEmpty
	@Column(name = "discount", nullable = false)
	private byte discount;

	@NotNull
	@NotEmpty
	@Column(name = "coupon", nullable = false, length = 250)
	private String coupon;

	@Column(name = "start", nullable = true)
	private LocalDate start;

	@Column(name = "end", nullable = true)
	private LocalDate end;

	@PrePersist
	protected void onCreate() {
		if (this.start == null) {
			this.start = LocalDate.now();
		}
		if (this.end == null) {
			this.end = this.start.plusMonths(1L);
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getSalename() {
		return salename;
	}

	public void setSalename(String salename) {
		this.salename = salename;
	}

	public byte getDiscount() {
		return discount;
	}

	public void setDiscount(byte discount) {
		this.discount = discount;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupon == null) ? 0 : coupon.hashCode());
		result = prime * result + discount;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((salename == null) ? 0 : salename.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + (status ? 1231 : 1237);
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
		Discount other = (Discount) obj;
		if (coupon == null) {
			if (other.coupon != null)
				return false;
		} else if (!coupon.equals(other.coupon))
			return false;
		if (discount != other.discount)
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (salename == null) {
			if (other.salename != null)
				return false;
		} else if (!salename.equals(other.salename))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	public boolean isNew() {
		return (this.id == null);
	}

}
