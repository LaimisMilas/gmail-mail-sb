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
import lt.gmail.mail.sender.model.GmailAPISettingEntity;
import lt.gmail.mail.sender.service.GmailApiSettingService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class GmailApiSettingController {
	
	@Autowired
	GmailApiSettingService service;

	@RequestMapping("/gmail/api/setting")
	public ResponseEntity<List<GmailAPISettingEntity>> getAll() {
		List<GmailAPISettingEntity> list = service.getAll();

		return new ResponseEntity<List<GmailAPISettingEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping("/gmail/api/setting/{id}")
	public ResponseEntity<GmailAPISettingEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		GmailAPISettingEntity entity = service.getById(id);

		return new ResponseEntity<GmailAPISettingEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, value="/gmail/api/setting", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GmailAPISettingEntity> createOrUpdate(@RequestBody GmailAPISettingEntity entity)
			throws RecordNotFoundException {
		GmailAPISettingEntity updated = service.createOrUpdate(entity);
		return new ResponseEntity<GmailAPISettingEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	} 
	
	@RequestMapping(method=RequestMethod.PUT, value="/gmail/api/setting", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody GmailAPISettingEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/gmail/api/setting/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}