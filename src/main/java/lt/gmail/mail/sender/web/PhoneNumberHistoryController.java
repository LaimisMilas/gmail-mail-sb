package lt.gmail.mail.sender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.PhoneNumberHistoryEntity;
import lt.gmail.mail.sender.service.PhoneNumberHistoryService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PhoneNumberHistoryController {

	@Autowired
	PhoneNumberHistoryService service;

	@RequestMapping(method = RequestMethod.GET, value = "/phone/number/historys")
	public ResponseEntity<List<PhoneNumberHistoryEntity>> getAll() {
		List<PhoneNumberHistoryEntity> list = service.getAll();

		return new ResponseEntity<List<PhoneNumberHistoryEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/phone/number/historys/{id}")
	public ResponseEntity<PhoneNumberHistoryEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		PhoneNumberHistoryEntity entity = service.getById(id);

		return new ResponseEntity<PhoneNumberHistoryEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/phone/number/historys", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhoneNumberHistoryEntity> create(@RequestBody PhoneNumberHistoryEntity enity) throws RecordNotFoundException {
		PhoneNumberHistoryEntity updated = service.createOrUpdate(enity);
		return new ResponseEntity<PhoneNumberHistoryEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/phone/number/historys", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody PhoneNumberHistoryEntity enity) throws RecordNotFoundException {
		service.createOrUpdate(enity);
	}

	@DeleteMapping("/phone/number/historys/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}
