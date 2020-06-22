package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.CompanyInfoEntity;
import lt.gmail.mail.sender.repository.CompanyInfoRepository;

 
@Service
public class CompanyInfoService {
     
    @Autowired
    CompanyInfoRepository repository;
     
    public List<CompanyInfoEntity> getAll()
    {
        List<CompanyInfoEntity> list = repository.findAll();
         
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<CompanyInfoEntity>();
        }
    }
     
    public CompanyInfoEntity getById(Long id) throws RecordNotFoundException
    {
        Optional<CompanyInfoEntity> entity = repository.findById(id);
         
        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
     
    public CompanyInfoEntity createOrUpdate(CompanyInfoEntity entity) throws RecordNotFoundException
    {
        Optional<CompanyInfoEntity> reservation = repository.findById(entity.getId());
         
        if(reservation.isPresent())
        {
        	CompanyInfoEntity newEntity = reservation.get();
            newEntity.setId(entity.getId());
            newEntity.setAddress(entity.getAddress());
            newEntity.setCompanyCode(entity.getCompanyCode());
            newEntity.setCompanyOwner(entity.getCompanyOwner());
            newEntity.setCompanyUrl(entity.getCompanyUrl());
            newEntity.setEmail(entity.getEmail());
            newEntity.setRawContacts(entity.getRawContacts());
            newEntity.setSales(entity.getSales());
            newEntity.setTitle(entity.getTitle());
            newEntity.setWebSiteUrl(entity.getWebSiteUrl());
            newEntity.setStatus(entity.getStatus());
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<CompanyInfoEntity> entity = repository.findById(id);
         
        if(entity.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No CompanyInfoEntity record exist for given id");
        }
    }

	public List<CompanyInfoEntity> searchByAddress(String address) {
		return repository.searchByAddress(address);
	}
	
	public List<CompanyInfoEntity> searchByEmail(String email) {
		return repository.searchByEmail(email);
	}
	
	public List<CompanyInfoEntity> searchByTitle(String title) {
		return repository.searchByTitle(title);
	}

	public List<CompanyInfoEntity> searchByCode(String code) {
		return repository.searchByCode(code);
	}
	
	public List<CompanyInfoEntity> searchInRawData(String text) {
		return repository.searchInRawData(text);
	}

}