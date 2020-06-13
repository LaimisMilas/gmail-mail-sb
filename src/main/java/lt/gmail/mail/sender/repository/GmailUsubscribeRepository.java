package lt.gmail.mail.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.GmailUnsubscribeEntity;

@Repository
public interface GmailUsubscribeRepository extends JpaRepository <GmailUnsubscribeEntity, Long> {	

	@Query("SELECT t FROM GmailUnsubscribeEntity t WHERE t.email = ?1 ")
	GmailUnsubscribeEntity findByEmail(String email);
}
