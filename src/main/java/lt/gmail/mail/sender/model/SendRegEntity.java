package lt.gmail.mail.sender.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;


@Entity
@Table(name = "send_email_logs")
public class SendRegEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long compaignId;
	
	@Column(name="company_code")
	private String companyCode;
	
	@Column(name="logs")
	private String logs;
	
	@Column(name="message")
	private String message;
	
	@Column(name = "created")
	private Date created;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public long getCompaignId() {
		return compaignId;
	}

	public void setCompaignId(long compaignId) {
		this.compaignId = compaignId;
	}
	
	
}
