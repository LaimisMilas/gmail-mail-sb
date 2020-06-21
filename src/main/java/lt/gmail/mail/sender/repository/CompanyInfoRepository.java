package lt.gmail.mail.sender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.CompanyInfoEntity;

@Repository
public interface CompanyInfoRepository extends JpaRepository <CompanyInfoEntity, Long> {

	@Query("SELECT t FROM CompanyInfoEntity t WHERE t.email LIKE %:email% ")
	List<CompanyInfoEntity> searchByEmail(String email);
	
	@Query("SELECT t FROM CompanyInfoEntity t WHERE t.rawContacts LIKE %:text% ")
	List<CompanyInfoEntity> searchInRawData(String text);
	
	@Query("SELECT t FROM CompanyInfoEntity t WHERE t.title LIKE %:title% ")
	List<CompanyInfoEntity> searchByTitle(String title);

	@Query("SELECT t FROM CompanyInfoEntity t WHERE t.code LIKE %:code% ")
	List<CompanyInfoEntity> searchByCode(String code);
	
	@Query("SELECT t FROM CompanyInfoEntity t WHERE t.address LIKE %:address% ")
	List<CompanyInfoEntity> searchByAddress(String address);

	@Query("SELECT t FROM CompanyInfoEntity t WHERE t.:colum LIKE %:text% ")
	List<CompanyInfoEntity> search(String colum, String text);
	
}
