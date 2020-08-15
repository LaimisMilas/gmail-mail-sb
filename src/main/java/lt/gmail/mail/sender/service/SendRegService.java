package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.SendRegEntity;
import lt.gmail.mail.sender.repository.SendRegRepository;

 
@Service
public class SendRegService {
     
    @Autowired
    SendRegRepository repository;
     
    public List<SendRegEntity> getAll()
    {
        List<SendRegEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<SendRegEntity>();
        }
    }
     
    public SendRegEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<SendRegEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
    
    public List<SendRegEntity> allIn24Hours(Long id) throws RecordNotFoundException
    {
    	long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    	long time = new Date().getTime() - MILLIS_PER_DAY;
    	Date before24 = new Date();
    	before24.setTime(time);
    	
        List<SendRegEntity> entitys = repository.allInInterval(id, before24, new Date());
        return entitys;
    }
     
    public SendRegEntity createOrUpdate(SendRegEntity entity) throws RecordNotFoundException
    {
        Optional<SendRegEntity> item = repository.findById(entity.getId());
         
        if(item.isPresent())
        {
        	SendRegEntity newEntity = item.get();
            newEntity.setId(entity.getId());
            newEntity.setCompanyCode(entity.getCompanyCode());
            newEntity.setCreated(entity.getCreated());
            newEntity.setLogs(entity.getLogs());
            newEntity.setMessage(entity.getMessage());
            newEntity = repository.save(newEntity);
 
            return newEntity;
        } else {
        	entity.setCreated(new Date());
        	entity = repository.save(entity);
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<SendRegEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }

	public List<SendRegEntity> seachInLogs(String key) {
		return repository.seachInLogs(key);
	}
}