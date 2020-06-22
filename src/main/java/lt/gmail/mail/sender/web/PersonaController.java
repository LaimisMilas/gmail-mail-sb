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
import lt.gmail.mail.sender.model.PersonaEntity;
import lt.gmail.mail.sender.service.PersonaService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PersonaController {

	@Autowired
	PersonaService service;

	@RequestMapping(method = RequestMethod.GET, value = "/persons")
	public ResponseEntity<List<PersonaEntity>> getAll() {
		List<PersonaEntity> list = service.getAll();

		return new ResponseEntity<List<PersonaEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/persons/{id}")
	public ResponseEntity<PersonaEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		PersonaEntity entity = service.getById(id);

		return new ResponseEntity<PersonaEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonaEntity> create(@RequestBody PersonaEntity enity) throws RecordNotFoundException {
		PersonaEntity updated = service.createOrUpdate(enity);
		return new ResponseEntity<PersonaEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody PersonaEntity enity) throws RecordNotFoundException {
		service.createOrUpdate(enity);
	}

	@DeleteMapping("/persons/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}
