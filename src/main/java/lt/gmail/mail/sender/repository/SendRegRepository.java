package lt.gmail.mail.sender.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.SendRegEntity;

@Repository
public interface SendRegRepository
        extends JpaRepository<SendRegEntity, Long> {
	
	@Query("SELECT t FROM SendRegEntity t WHERE t.logs LIKE %:logs% ")
	List<SendRegEntity> seachInLogs(@Param("logs") String logs);
	
	@Query("SELECT t FROM SendRegEntity t WHERE t.companyCode = ?1 ")
	List<SendRegEntity> selectAllByCompanyCode(String companyCode);

	@Query("SELECT t FROM SendRegEntity t WHERE t.compaignId = ?1 and t.created > ?2 and t.created <= ?3")
	List<SendRegEntity> allInInterval(Long compaignId, Date dateTrom, Date dateTo);

}
