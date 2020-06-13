package lt.gmail.mail.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.gmail.mail.sender.model.UserRoleEnitity;
 
@Repository
public interface UserRoleRepository
        extends JpaRepository<UserRoleEnitity, Long> {
 
}
