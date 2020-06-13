package lt.gmail.mail.sender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import lt.gmail.mail.sender.model.GmailHTMLEntity;

@Repository
public interface EmailHTMLRepository extends JpaRepository <GmailHTMLEntity, Long> {
	
	@Query("SELECT t FROM GmailHTMLEntity t WHERE t.userId = ?1 ")
	List<GmailHTMLEntity> getAllByUserId(Long id);
	
}
