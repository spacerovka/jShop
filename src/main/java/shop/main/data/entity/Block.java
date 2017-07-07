package shop.main.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "block")
public class Block {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/**
	 * @param content
	 *            is html wth data you need to display
	 */
	@Column(name = "content", nullable = true, columnDefinition = "TEXT")
	private String content;

	@NotBlank
	@Column(name = "position", nullable = false, length = 50)
	private String position;

	/**
	 * @param blockURL
	 *            is used to display block on particular page
	 */
	@Column(name = "blockURL", nullable = true, length = 200)
	private String blockURL;

	@NotNull
	@Column(name = "status", nullable = false)
	private boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBlockURL() {
		return blockURL;
	}

	public void setBlockURL(String URL) {
		blockURL = URL;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blockURL == null) ? 0 : blockURL.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		Block other = (Block) obj;
		if (blockURL == null) {
			if (other.blockURL != null)
				return false;
		} else if (!blockURL.equals(other.blockURL))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status != other.status)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Blocks [id=" + id + ", content=" + content + ", position=" + position + ", blockURL=" + blockURL
				+ ", status=" + status + "]";
	}

	public boolean isNew() {
		return (this.id == null);
	}

}
