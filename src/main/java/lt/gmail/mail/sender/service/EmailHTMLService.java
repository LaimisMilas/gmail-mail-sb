package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.GmailHTMLEntity;
import lt.gmail.mail.sender.repository.EmailHTMLRepository;

@Service
public class EmailHTMLService {
     
    @Autowired
    EmailHTMLRepository repository;
     
    public List<GmailHTMLEntity> getAll()
    {
        List<GmailHTMLEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<GmailHTMLEntity>();
        }
    }
     
    public GmailHTMLEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<GmailHTMLEntity> emailHtml = repository.findById(id);
         
        if(emailHtml.isPresent()) {
            return emailHtml.get();
        } else {
            throw new RecordNotFoundException("No emailHtml record exist for given id");
        }
    }
     
    public GmailHTMLEntity createOrUpdate(GmailHTMLEntity entity) throws RecordNotFoundException
    {
        Optional<GmailHTMLEntity> gmailHTML = repository.findById(entity.getId());
        if(gmailHTML.isPresent())
        {
        	GmailHTMLEntity newEntity = gmailHTML.get();
            newEntity.setTitle(entity.getTitle());
            newEntity.setHtmlContent(entity.getHtmlContent());
            newEntity = repository.save(newEntity);
            return newEntity;
        } else {
            entity = repository.save(entity);
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<GmailHTMLEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No GmailHTMLEntity record exist for given id");
        }
    }

	public List<GmailHTMLEntity> getAllByUserId(Long id) throws RecordNotFoundException {
		List<GmailHTMLEntity> emailHtml = repository.getAllByUserId(id);
        
        if(emailHtml != null) {
            return emailHtml;
        } else {
            throw new RecordNotFoundException("No GmailHTMLEntity record exist for given id");
        }
	}
}