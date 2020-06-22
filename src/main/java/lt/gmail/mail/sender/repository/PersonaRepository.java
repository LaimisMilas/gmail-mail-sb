package lt.gmail.mail.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import lt.gmail.mail.sender.model.PersonaEntity;
 
@Repository
public interface PersonaRepository
        extends JpaRepository<PersonaEntity, Long> {
}
