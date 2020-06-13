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
import lt.gmail.mail.sender.model.GmailCampaignEntity;
import lt.gmail.mail.sender.model.GmailHTMLEntity;
import lt.gmail.mail.sender.service.GmailCampaignService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EmailCompagneController {
	
	@Autowired
	GmailCampaignService service;

	@RequestMapping("/gmail/campaigns")
	public ResponseEntity<List<GmailCampaignEntity>> getAll() {
		List<GmailCampaignEntity> list = service.getAll();

		return new ResponseEntity<List<GmailCampaignEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping("/gmail/user/{id}/campaigns")
	public ResponseEntity<List<GmailCampaignEntity>> getAllByUserId(@PathVariable("id") Long id) throws RecordNotFoundException {
		List<GmailCampaignEntity> list = service.getAllByUserId(id);

		return new ResponseEntity<List<GmailCampaignEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping("/gmail/campaigns/{id}")
	public ResponseEntity<GmailCampaignEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		GmailCampaignEntity entity = service.getById(id);

		return new ResponseEntity<GmailCampaignEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, value="/gmail/campaigns", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GmailCampaignEntity> createOrUpdate(@RequestBody GmailCampaignEntity entity)
			throws RecordNotFoundException {
		GmailCampaignEntity updated = service.createOrUpdate(entity);
		return new ResponseEntity<GmailCampaignEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	} 
	
	@RequestMapping(method=RequestMethod.PUT, value="/gmail/campaigns", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody GmailCampaignEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/gmail/campaigns/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}