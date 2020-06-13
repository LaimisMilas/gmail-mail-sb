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
import lt.gmail.mail.sender.model.UserEntity;
import lt.gmail.mail.sender.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService service;

	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public ResponseEntity<List<UserEntity>> getAll() {
		List<UserEntity> list = service.getAll();

		return new ResponseEntity<List<UserEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
	public ResponseEntity<UserEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		UserEntity entity = service.getById(id);

		return new ResponseEntity<UserEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/current")
	public ResponseEntity<UserEntity> getCurrentUser() throws RecordNotFoundException {
		UserEntity entity = service.getCurrentUser();
		return new ResponseEntity<UserEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> create(@RequestBody UserEntity enity) throws RecordNotFoundException {
		UserEntity updated = service.createOrUpdate(enity);
		return new ResponseEntity<UserEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody UserEntity enity) throws RecordNotFoundException {
		service.createOrUpdate(enity);
	}

	@DeleteMapping("/users/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}
