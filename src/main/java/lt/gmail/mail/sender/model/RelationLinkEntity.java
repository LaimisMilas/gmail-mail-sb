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
@Table(name = "relation_link")
public class RelationLinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="email_id")
    private Long emailId;
    @Column(name="phone_id")
    private Long phoneId;
    @Column(name="persona_id")
    private Long personaId;
    @Column(name="company_id")
    private Long companyId;
    @Column(name="friend_id")
    private Long friendId;
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Long getEmailId(){
        return emailId;
    }

    public void setEmailId(Long emailId){
        this.emailId = emailId;
    }
    public Long getPhoneId(){
        return phoneId;
    }

    public void setPhoneId(Long phoneId){
        this.phoneId = phoneId;
    }
    public Long getPersonaId(){
        return personaId;
    }

    public void setPersonaId(Long personaId){
        this.personaId = personaId;
    }
    public Long getCompanyId(){
        return companyId;
    }

    public void setCompanyId(Long companyId){
        this.companyId = companyId;
    }

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
    
    
}