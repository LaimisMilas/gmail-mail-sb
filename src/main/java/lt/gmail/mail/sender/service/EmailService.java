package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.EmailEntity;
import lt.gmail.mail.sender.repository.EmailRepository;


@Service
public class EmailService {

    @Autowired
        EmailRepository repository;

    public List<EmailEntity> getAll()
    {
        List<EmailEntity> list = repository.findAll();

        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<EmailEntity>();
        }
    }

    public EmailEntity getById(Long id) throws RecordNotFoundException
{
    Optional<EmailEntity> entity = repository.findById(id);

    if(entity.isPresent()) {
    return entity.get();
} else {
    throw new RecordNotFoundException("No Message record exist for given id");
}
}

public EmailEntity createOrUpdate(EmailEntity entity) throws RecordNotFoundException
{
    Optional<EmailEntity> item = repository.findById(entity.getId());
    if(item.isPresent())
    {
        EmailEntity newEntity = item.get();
        newEntity.setId(entity.getId());
        newEntity.setEmail(entity.getEmail());
        newEntity.setDomain(entity.getDomain());
        newEntity.setEmailHistory(entity.getEmailHistory());
        newEntity = repository.save(newEntity);
        return newEntity;
    } else {
        entity = repository.save(entity);
        return entity;
    }
}

public void deleteById(Long id) throws RecordNotFoundException
{
    Optional<EmailEntity> entity = repository.findById(id);

    if(entity.isPresent())
    {
        repository.deleteById(id);
    } else {
        throw new RecordNotFoundException("No Message record exist for given id");
    }
}
}