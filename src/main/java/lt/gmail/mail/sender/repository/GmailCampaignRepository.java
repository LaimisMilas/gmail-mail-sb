package lt.gmail.mail.sender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import lt.gmail.mail.sender.model.GmailCampaignEntity;

@Repository
public interface GmailCampaignRepository extends JpaRepository <GmailCampaignEntity, Long> {
	
	@Query("SELECT t FROM GmailCampaignEntity t WHERE t.userId = ?1 ")
	List<GmailCampaignEntity> getAllByUserId(Long id);
	
}
