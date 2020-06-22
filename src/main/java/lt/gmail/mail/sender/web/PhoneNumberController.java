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
import lt.gmail.mail.sender.model.PhoneNumberEntity;
import lt.gmail.mail.sender.service.PhoneNumberService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PhoneNumberController {

	@Autowired
	PhoneNumberService service;

	@RequestMapping(method = RequestMethod.GET, value = "/phone/numbers")
	public ResponseEntity<List<PhoneNumberEntity>> getAll() {
		List<PhoneNumberEntity> list = service.getAll();

		return new ResponseEntity<List<PhoneNumberEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/phone/numbers/{id}")
	public ResponseEntity<PhoneNumberEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		PhoneNumberEntity entity = service.getById(id);

		return new ResponseEntity<PhoneNumberEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/phone/numbers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhoneNumberEntity> create(@RequestBody PhoneNumberEntity enity) throws RecordNotFoundException {
		PhoneNumberEntity updated = service.createOrUpdate(enity);
		return new ResponseEntity<PhoneNumberEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/phone/numbers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody PhoneNumberEntity enity) throws RecordNotFoundException {
		service.createOrUpdate(enity);
	}

	@DeleteMapping("/phone/numbers/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}
