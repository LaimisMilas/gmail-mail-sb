package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.PhoneNumberEntity;
import lt.gmail.mail.sender.repository.PhoneNumberRepository;

 
@Service
public class PhoneNumberService {
     
    @Autowired
    PhoneNumberRepository repository;
     
    public List<PhoneNumberEntity> getAll()
    {
        List<PhoneNumberEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<PhoneNumberEntity>();
        }
    }
     
    public PhoneNumberEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<PhoneNumberEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
     
    public PhoneNumberEntity createOrUpdate(PhoneNumberEntity entity) throws RecordNotFoundException
    {
        Optional<PhoneNumberEntity> item = repository.findById(entity.getId());
         
        if(item.isPresent())
        {
        	PhoneNumberEntity newEntity = item.get();
            newEntity.setId(entity.getId());
            newEntity.setNumber(entity.getNumber());
            newEntity.setOperator(entity.getOperator());
            newEntity.setNumberHistory(entity.getNumberHistory());   
            newEntity = repository.save(newEntity);
 
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<PhoneNumberEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
}