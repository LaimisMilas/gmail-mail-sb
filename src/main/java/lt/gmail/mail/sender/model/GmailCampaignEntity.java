package lt.gmail.mail.sender.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "gmail_campaign")
public class GmailCampaignEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "title")
	private String title;
	
	@Column(name = "subject_line")
	private String subjectLine;
	
	@Column(name = "sender")
	private String sender;
	
	@Column(name = "log_key")
	private String logKey;
	
	@Column(name = "unsubscribe_list_id")
	private int unsubscribeListId;
	
	@CreatedDate
	@Column(name = "created")
	private Date created;
	
	@ManyToOne(targetEntity = GmailHTMLEntity.class)
	GmailHTMLEntity gmailHTML;
	
	@ManyToOne(targetEntity = CompanyInfoRecipientListEntity.class)
	CompanyInfoRecipientListEntity recipientList;

	public CompanyInfoRecipientListEntity getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(CompanyInfoRecipientListEntity recipientList) {
		this.recipientList = recipientList;
	}

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

	public String getSubjectLine() {
		return subjectLine;
	}

	public void setSubjectLine(String subjectLine) {
		this.subjectLine = subjectLine;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}

	public int getUnsubscribeListId() {
		return unsubscribeListId;
	}

	public void setUnsubscribeListId(int unsubscribeListId) {
		this.unsubscribeListId = unsubscribeListId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public GmailHTMLEntity getGmailHTML() {
		return gmailHTML;
	}

	public void setGmailHTML(GmailHTMLEntity gmailHTML) {
		this.gmailHTML = gmailHTML;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
