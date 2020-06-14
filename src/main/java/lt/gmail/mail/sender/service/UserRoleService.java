package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.UserRoleEntity;
import lt.gmail.mail.sender.repository.UserRoleRepository;

 
@Service
public class UserRoleService {
     
    @Autowired
    UserRoleRepository repository;
     
    public List<UserRoleEntity> getAll()
    {
        List<UserRoleEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<UserRoleEntity>();
        }
    }
     
    public UserRoleEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<UserRoleEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
     
    public UserRoleEntity createOrUpdate(UserRoleEntity entity) throws RecordNotFoundException
    {
        Optional<UserRoleEntity> reservation = repository.findById(entity.getId());
         
        if(reservation.isPresent())
        {
        	UserRoleEntity newEntity = reservation.get();
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
        Optional<UserRoleEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }

	public UserRoleEntity getByName(String roleName) {
		return repository.getByName(roleName);
	}
}