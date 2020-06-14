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
import lt.gmail.mail.sender.model.UserRoleEntity;
import lt.gmail.mail.sender.service.UserRoleService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserRoleController {
	
	@Autowired
	UserRoleService service;

	@RequestMapping("/userroles")
	public ResponseEntity<List<UserRoleEntity>> getAll() {
		List<UserRoleEntity> list = service.getAll();

		return new ResponseEntity<List<UserRoleEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping("/userroles/{id}")
	public ResponseEntity<UserRoleEntity> getById(@PathVariable Long id) throws RecordNotFoundException {
		UserRoleEntity entity = service.getById(id);

		return new ResponseEntity<UserRoleEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/userroles", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void create(@RequestBody UserRoleEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/userroles", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody UserRoleEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/userroles/{id}")
	public HttpStatus deleteById(@PathVariable Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}