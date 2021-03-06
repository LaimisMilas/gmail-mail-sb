package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.EmailHistoryEntity;
import lt.gmail.mail.sender.repository.EmailHistoryRepository;


@Service
public class EmailHistoryService {

    @Autowired
        EmailHistoryRepository repository;

    public List<EmailHistoryEntity> getAll()
    {
        List<EmailHistoryEntity> list = repository.findAll();

        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<EmailHistoryEntity>();
        }
    }

    public EmailHistoryEntity getById(Long id) throws RecordNotFoundException
{
    Optional<EmailHistoryEntity> entity = repository.findById(id);

    if(entity.isPresent()) {
    return entity.get();
} else {
    throw new RecordNotFoundException("No Message record exist for given id");
}
}

public EmailHistoryEntity createOrUpdate(EmailHistoryEntity entity) throws RecordNotFoundException
{
    Optional<EmailHistoryEntity> item = repository.findById(entity.getId());
    if(item.isPresent())
    {
        EmailHistoryEntity newEntity = item.get();
        newEntity.setId(entity.getId());
        newEntity.setText(entity.getText());
        newEntity = repository.save(newEntity);
        return newEntity;
    } else {
        entity = repository.save(entity);
        return entity;
    }
}

public void deleteById(Long id) throws RecordNotFoundException
{
    Optional<EmailHistoryEntity> entity = repository.findById(id);

    if(entity.isPresent())
    {
        repository.deleteById(id);
    } else {
        throw new RecordNotFoundException("No Message record exist for given id");
    }
}
}