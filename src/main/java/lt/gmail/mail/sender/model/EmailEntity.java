package lt.gmail.mail.sender.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "email")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String domain;
    
	@OneToMany(targetEntity = EmailHistoryEntity.class)
	private List<EmailHistoryEntity> emailHistory = new ArrayList<EmailHistoryEntity>();

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getDomain(){
        return domain;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

	public List<EmailHistoryEntity> getEmailHistory() {
		return emailHistory;
	}

	public void setEmailHistory(List<EmailHistoryEntity> emailHistory) {
		this.emailHistory = emailHistory;
	}
    
    
    
}  