package lt.gmail.mail.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.UserRoleEntity;
 
@Repository
public interface UserRoleRepository
        extends JpaRepository<UserRoleEntity, Long> {
	
	@Query("SELECT t FROM UserRoleEntity t WHERE t.role = ?1 ")
	UserRoleEntity getByName(String roleName);
 
}
