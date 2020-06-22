package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.PhoneNumberHistoryEntity;
import lt.gmail.mail.sender.repository.PhoneNumberHistoryRepository;

 
@Service
public class PhoneNumberHistoryService {
     
    @Autowired
    PhoneNumberHistoryRepository repository;
     
    public List<PhoneNumberHistoryEntity> getAll()
    {
        List<PhoneNumberHistoryEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<PhoneNumberHistoryEntity>();
        }
    }
     
    public PhoneNumberHistoryEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<PhoneNumberHistoryEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
     
    public PhoneNumberHistoryEntity createOrUpdate(PhoneNumberHistoryEntity entity) throws RecordNotFoundException
    {
        Optional<PhoneNumberHistoryEntity> item = repository.findById(entity.getId());
         
        if(item.isPresent())
        {
        	PhoneNumberHistoryEntity newEntity = item.get();
            newEntity.setId(entity.getId());
            newEntity.setText(entity.getText());
            
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<PhoneNumberHistoryEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
}