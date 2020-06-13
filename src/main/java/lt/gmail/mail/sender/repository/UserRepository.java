package lt.gmail.mail.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.UserEntity;
 
@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, Long> {
 
	public UserEntity findByUserName(String userName);
	
}
