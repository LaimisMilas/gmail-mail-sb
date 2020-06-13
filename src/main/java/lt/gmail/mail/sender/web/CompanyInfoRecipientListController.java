package lt.gmail.mail.sender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.CompanyInfoRecipientListEntity;
import lt.gmail.mail.sender.service.CompanyInfoRecipientListService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CompanyInfoRecipientListController {

	@Autowired
	private CompanyInfoRecipientListService service;

	@RequestMapping("/company/info/recipients")
	public ResponseEntity<List<CompanyInfoRecipientListEntity>> getAll() {
		List<CompanyInfoRecipientListEntity> list = service.getAll();

		return new ResponseEntity<List<CompanyInfoRecipientListEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping("/company/info/user/{id}/recipients")
	public ResponseEntity<List<CompanyInfoRecipientListEntity>> getAllByUserId(@PathVariable("id") Long id) throws RecordNotFoundException {
		List<CompanyInfoRecipientListEntity> list = service.getAllByUserId(id);

		return new ResponseEntity<List<CompanyInfoRecipientListEntity>>(list, new HttpHeaders(), HttpStatus.OK);
		}

	@RequestMapping("/company/info/recipients/{id}")
	public ResponseEntity<CompanyInfoRecipientListEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		CompanyInfoRecipientListEntity entity = service.getById(id);

		return new ResponseEntity<CompanyInfoRecipientListEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, value="/company/info/recipients", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanyInfoRecipientListEntity> createOrUpdate(@RequestBody CompanyInfoRecipientListEntity entity)
			throws RecordNotFoundException {
		CompanyInfoRecipientListEntity updated = service.createOrUpdate(entity);
		return new ResponseEntity<CompanyInfoRecipientListEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	} 
	
	@RequestMapping(method=RequestMethod.PUT, value="/company/info/recipients", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody CompanyInfoRecipientListEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/company/info/recipients/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}
}
