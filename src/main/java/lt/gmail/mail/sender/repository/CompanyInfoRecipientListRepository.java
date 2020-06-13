package lt.gmail.mail.sender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.CompanyInfoRecipientListEntity;

@Repository
public interface CompanyInfoRecipientListRepository extends JpaRepository <CompanyInfoRecipientListEntity, Long> {

	@Query("SELECT t FROM CompanyInfoRecipientListEntity t WHERE t.userId = ?1 ")
	List<CompanyInfoRecipientListEntity> getByUserId(Long id);
	
}
