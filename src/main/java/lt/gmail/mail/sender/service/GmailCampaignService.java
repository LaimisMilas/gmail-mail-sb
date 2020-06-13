package lt.gmail.mail.sender.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.GmailCampaignEntity;
import lt.gmail.mail.sender.repository.GmailCampaignRepository;

@Service
public class GmailCampaignService {
	
	@Autowired
	public GmailCampaignRepository repository;
	
    public List<GmailCampaignEntity> getAll()
    {
        List<GmailCampaignEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<GmailCampaignEntity>();
        }
    }
    
	public List<GmailCampaignEntity> getAllByUserId(Long id) throws RecordNotFoundException {
		List<GmailCampaignEntity> list = repository.getAllByUserId(id);
        
        if(list != null) {
            return list;
        } else {
            throw new RecordNotFoundException("No GmailCampaignEntity record exist for given id");
        }
	}
     
    public GmailCampaignEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<GmailCampaignEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No GmailCampaignEntity record exist for given id");
        }
    }
     
    public GmailCampaignEntity createOrUpdate(GmailCampaignEntity entity) throws RecordNotFoundException
    {
        Optional<GmailCampaignEntity> gmailUnsubscribe = repository.findById(entity.getId());
        if(gmailUnsubscribe.isPresent())
        {
        	GmailCampaignEntity newEntity = gmailUnsubscribe.get();
            newEntity.setLogKey(entity.getLogKey());
            newEntity.setTitle(entity.getTitle());
            newEntity.setSender(entity.getSender());
            newEntity.setSubjectLine(entity.getSubjectLine());
            newEntity.setRecipientList(entity.getRecipientList());
            newEntity.setGmailHTML(entity.getGmailHTML());
            newEntity.setUnsubscribeListId(entity.getUnsubscribeListId());
            
            newEntity = repository.save(newEntity);
            return newEntity;
        } else {
            entity = repository.save(entity);
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<GmailCampaignEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No GmailCampaignEntity record exist for given id");
        }
    }
}
