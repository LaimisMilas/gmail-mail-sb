package lt.gmail.mail.sender.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "company_info_recipient_list")
public class CompanyInfoRecipientListEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "title")
	private String title;
	
	@CreatedDate
	@Column(name = "created")
	private Date created;
	
	@ManyToMany(targetEntity = CompanyInfoEntity.class)
	List<CompanyInfoEntity> recipients = new ArrayList<CompanyInfoEntity>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<CompanyInfoEntity> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<CompanyInfoEntity> recipients) {
		this.recipients = recipients;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
