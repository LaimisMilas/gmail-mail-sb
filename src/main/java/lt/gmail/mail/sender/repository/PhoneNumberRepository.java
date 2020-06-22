package lt.gmail.mail.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import lt.gmail.mail.sender.model.PhoneNumberEntity;
 
@Repository
public interface PhoneNumberRepository
        extends JpaRepository<PhoneNumberEntity, Long> {
}
