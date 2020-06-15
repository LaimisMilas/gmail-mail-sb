package lt.gmail.mail.sender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.GmailAPISettingEntity;

@Repository
public interface GmailApiSettingRepository extends JpaRepository <GmailAPISettingEntity, Long> {

	@Query("SELECT t FROM GmailAPISettingEntity t WHERE t.user.id = ?1 ")
	List<GmailAPISettingEntity> getByUserId(Long id);

	@Query("SELECT t FROM GmailAPISettingEntity t WHERE t.user.id = ?1 and t.defaultConfig = true")
	GmailAPISettingEntity getDefaulByUserId(Long id);
}
