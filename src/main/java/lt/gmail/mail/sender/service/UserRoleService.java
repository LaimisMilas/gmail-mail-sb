package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.UserRoleEnitity;
import lt.gmail.mail.sender.repository.UserRoleRepository;

 
@Service
public class UserRoleService {
     
    @Autowired
    UserRoleRepository repository;
     
    public List<UserRoleEnitity> getAll()
    {
        List<UserRoleEnitity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<UserRoleEnitity>();
        }
    }
     
    public UserRoleEnitity getById(Long id) throws RecordNotFoundException
    {
        Optional<UserRoleEnitity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
     
    public UserRoleEnitity createOrUpdate(UserRoleEnitity entity) throws RecordNotFoundException
    {
        Optional<UserRoleEnitity> reservation = repository.findById(entity.getId());
         
        if(reservation.isPresent())
        {
        	UserRoleEnitity newEntity = reservation.get();
            newEntity.setId(entity.getId());
            newEntity.setRole(entity.getRole());
            
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<UserRoleEnitity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
}