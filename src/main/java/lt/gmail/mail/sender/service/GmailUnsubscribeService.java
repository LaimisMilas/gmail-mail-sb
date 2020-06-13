package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.GmailUnsubscribeEntity;
import lt.gmail.mail.sender.repository.GmailUsubscribeRepository;

@Service
public class GmailUnsubscribeService {
     
    @Autowired
    GmailUsubscribeRepository repository;
     
    public List<GmailUnsubscribeEntity> getAll()
    {
        List<GmailUnsubscribeEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<GmailUnsubscribeEntity>();
        }
    }
     
    public GmailUnsubscribeEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<GmailUnsubscribeEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No GmailUnsubscribeEntity record exist for given id");
        }
    }
     
    public GmailUnsubscribeEntity createOrUpdate(GmailUnsubscribeEntity entity) throws RecordNotFoundException
    {
        Optional<GmailUnsubscribeEntity> gmailUnsubscribe = repository.findById(entity.getId());
        if(gmailUnsubscribe.isPresent())
        {
        	GmailUnsubscribeEntity newEntity = gmailUnsubscribe.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setLogKey(entity.getLogKey());           
            newEntity = repository.save(newEntity);
            return newEntity;
        } else {
            entity = repository.save(entity);
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<GmailUnsubscribeEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No GmailUnsubscribeEntity record exist for given id");
        }
    }
}