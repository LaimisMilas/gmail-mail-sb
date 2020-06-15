package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.GmailAPISettingEntity;
import lt.gmail.mail.sender.repository.GmailApiSettingRepository;

@Service
public class GmailApiSettingService {
     
    @Autowired
    GmailApiSettingRepository repository;
     
    public List<GmailAPISettingEntity> getAll()
    {
        List<GmailAPISettingEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<GmailAPISettingEntity>();
        }
    }
     
    public GmailAPISettingEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<GmailAPISettingEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No GmailAPISettingEntity record exist for given id");
        }
    }
     
    public GmailAPISettingEntity createOrUpdate(GmailAPISettingEntity entity) throws RecordNotFoundException
    {
        Optional<GmailAPISettingEntity> gm = repository.findById(entity.getId());
        if(gm.isPresent())
        {
        	GmailAPISettingEntity newEntity = gm.get();
            newEntity.setApplicationName(entity.getApplicationName());
            newEntity.setClientId(entity.getClientId());
            newEntity.setClientSecret(entity.getClientSecret());
            newEntity.setRedirectUri(entity.getRedirectUri());            
            newEntity.setAccessTokenUri(entity.getAccessTokenUri());            
            newEntity.setClientAuthenticationScheme(entity.getClientAuthenticationScheme());            
            newEntity.setPreferTokenInfo(entity.getPreferTokenInfo());            
            newEntity.setScope(entity.getScope());
            newEntity.setDefaultConfig(entity.isDefaultConfig());
            newEntity.setUserAuthorizationUri(entity.getUserAuthorizationUri());
            
            newEntity = repository.save(newEntity);
            return newEntity;
        } else {
            entity = repository.save(entity);
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<GmailAPISettingEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No GmailAPISettingEntity record exist for given id");
        }
    }
    
    public GmailAPISettingEntity getDefaulByUserId(Long id) {
		return repository.getDefaulByUserId(id);
	}

	public List<GmailAPISettingEntity> getByUserId(Long id) {
		return repository.getByUserId(id);
	}
}