package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.CompanyInfoRecipientListEntity;
import lt.gmail.mail.sender.repository.CompanyInfoRecipientListRepository;

@Service
public class CompanyInfoRecipientListService {
     
    @Autowired
    CompanyInfoRecipientListRepository repository;
     
    public List<CompanyInfoRecipientListEntity> getAll()
    {
        List<CompanyInfoRecipientListEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<CompanyInfoRecipientListEntity>();
        }
    }

    public CompanyInfoRecipientListEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<CompanyInfoRecipientListEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No CompanyInfoRecipientListEntity record exist for given id");
        }
    }
     
    public CompanyInfoRecipientListEntity createOrUpdate(CompanyInfoRecipientListEntity entity) throws RecordNotFoundException
    {
        Optional<CompanyInfoRecipientListEntity> gmailCompaign = repository.findById(entity.getId());
        if(gmailCompaign.isPresent())
        {
        	CompanyInfoRecipientListEntity newEntity = gmailCompaign.get();
            newEntity.setTitle(entity.getTitle());
            newEntity.setRecipients(entity.getRecipients());
            newEntity = repository.save(newEntity);
            return newEntity;
        } else {
            entity = repository.save(entity);
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<CompanyInfoRecipientListEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No CompanyInfoRecipientListEntity record exist for given id");
        }
    }

	public List<CompanyInfoRecipientListEntity> getAllByUserId(Long id) throws RecordNotFoundException {
		List<CompanyInfoRecipientListEntity> entity = repository.getByUserId(id);
	        if(entity != null) {
	            return entity;
	        } else {
	            throw new RecordNotFoundException("No CompanyInfoRecipientListEntity record exist for given id");
	        }
	}
}