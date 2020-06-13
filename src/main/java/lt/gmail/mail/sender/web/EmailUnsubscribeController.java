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
import lt.gmail.mail.sender.model.GmailHTMLEntity;
import lt.gmail.mail.sender.model.GmailUnsubscribeEntity;
import lt.gmail.mail.sender.repository.GmailUsubscribeRepository;
import lt.gmail.mail.sender.service.EmailHTMLService;
import lt.gmail.mail.sender.service.GmailUnsubscribeService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EmailUnsubscribeController {
	
	@Autowired
	GmailUnsubscribeService service;

	@RequestMapping("/gmailunsubscribes")
	public ResponseEntity<List<GmailUnsubscribeEntity>> getAll() {
		List<GmailUnsubscribeEntity> list = service.getAll();

		return new ResponseEntity<List<GmailUnsubscribeEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping("/gmailunsubscribes/{id}")
	public ResponseEntity<GmailUnsubscribeEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		GmailUnsubscribeEntity entity = service.getById(id);

		return new ResponseEntity<GmailUnsubscribeEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, value="/gmailunsubscribes", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GmailUnsubscribeEntity> createOrUpdate(@RequestBody GmailUnsubscribeEntity entity)
			throws RecordNotFoundException {
		GmailUnsubscribeEntity updated = service.createOrUpdate(entity);
		return new ResponseEntity<GmailUnsubscribeEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	} 
	
	@RequestMapping(method=RequestMethod.PUT, value="/gmailunsubscribes", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody GmailUnsubscribeEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/gmailunsubscribes/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}