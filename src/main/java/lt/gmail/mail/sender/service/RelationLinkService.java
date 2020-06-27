package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.RelationLinkEntity;
import lt.gmail.mail.sender.repository.RelationLinkRepository;


@Service
public class RelationLinkService {

    @Autowired
        RelationLinkRepository repository;

    public List<RelationLinkEntity> getAll()
    {
        List<RelationLinkEntity> list = repository.findAll();

        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<RelationLinkEntity>();
        }
    }

    public RelationLinkEntity getById(Long id) throws RecordNotFoundException
{
    Optional<RelationLinkEntity> entity = repository.findById(id);

    if(entity.isPresent()) {
    return entity.get();
} else {
    throw new RecordNotFoundException("No Message record exist for given id");
}
}

public RelationLinkEntity createOrUpdate(RelationLinkEntity entity) throws RecordNotFoundException
{
    Optional<RelationLinkEntity> item = repository.findById(entity.getId());
    if(item.isPresent())
    {
        RelationLinkEntity newEntity = item.get();
        newEntity.setId(entity.getId());
        newEntity.setEmailId(entity.getEmailId());
        newEntity.setPhoneId(entity.getPhoneId());
        newEntity.setPersonaId(entity.getPersonaId());
        newEntity.setCompanyId(entity.getCompanyId());
        newEntity = repository.save(newEntity);
        return newEntity;
    } else {
        entity = repository.save(entity);
        return entity;
    }
}

public void deleteById(Long id) throws RecordNotFoundException
{
    Optional<RelationLinkEntity> entity = repository.findById(id);

    if(entity.isPresent())
    {
        repository.deleteById(id);
    } else {
        throw new RecordNotFoundException("No Message record exist for given id");
    }
}
}