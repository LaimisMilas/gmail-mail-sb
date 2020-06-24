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
@Table(name = "email_history")
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}    