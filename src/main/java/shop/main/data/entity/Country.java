package shop.main.data.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "country")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@NotBlank
	@Column(name = "basetarif", nullable = false, precision = 12, scale = 2)
	private BigDecimal basetarif;

	@NotNull
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<ParcelCost> costList;

	public Country() {
		basetarif = new BigDecimal(0);
		costList = new ArrayList<ParcelCost>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBasetarif() {
		return basetarif;
	}

	public void setBasetarif(BigDecimal basetarif) {
		this.basetarif = basetarif;
	}

	public List<ParcelCost> getCostList() {
		return costList;
	}

	public void setCostList(List<ParcelCost> costList) {
		this.costList = costList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basetarif == null) ? 0 : basetarif.hashCode());
		result = prime * result + ((costList == null) ? 0 : costList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Country other = (Country) obj;
		if (basetarif == null) {
			if (other.basetarif != null)
				return false;
		} else if (!basetarif.equals(other.basetarif))
			return false;
		if (costList == null) {
			if (other.costList != null)
				return false;
		} else if (!costList.equals(other.costList))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isNew() {
		return (this.id == null);
	}

}
