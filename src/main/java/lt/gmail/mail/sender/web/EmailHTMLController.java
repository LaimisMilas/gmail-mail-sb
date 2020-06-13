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
import lt.gmail.mail.sender.service.EmailHTMLService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EmailHTMLController {
	
	@Autowired
	EmailHTMLService service;

	@RequestMapping("/email/html")
	public ResponseEntity<List<GmailHTMLEntity>> getAll() {
		List<GmailHTMLEntity> list = service.getAll();

		return new ResponseEntity<List<GmailHTMLEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping("/email/user/{id}/html")
	public ResponseEntity<List<GmailHTMLEntity>> getAllByUserId(@PathVariable("id") Long id) throws RecordNotFoundException {
		List<GmailHTMLEntity> list = service.getAllByUserId(id);

		return new ResponseEntity<List<GmailHTMLEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping("/email/html/{id}")
	public ResponseEntity<GmailHTMLEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		GmailHTMLEntity entity = service.getById(id);

		return new ResponseEntity<GmailHTMLEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, value="/email/html", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GmailHTMLEntity> createOrUpdate(@RequestBody GmailHTMLEntity entity)
			throws RecordNotFoundException {
		GmailHTMLEntity updated = service.createOrUpdate(entity);
		return new ResponseEntity<GmailHTMLEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	} 
	
	@RequestMapping(method=RequestMethod.PUT, value="/email/html", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody GmailHTMLEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/email/html/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}