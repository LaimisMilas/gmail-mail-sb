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
import lt.gmail.mail.sender.model.RelationLinkEntity;
import lt.gmail.mail.sender.service.RelationLinkService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RelationLinkController {

	@Autowired
	RelationLinkService service;

	@RequestMapping(method = RequestMethod.GET, value = "/relation/links")
	public ResponseEntity<List<RelationLinkEntity>> getAll() {
		List<RelationLinkEntity> list = service.getAll();

		return new ResponseEntity<List<RelationLinkEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/relation/links/{id}")
	public ResponseEntity<RelationLinkEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		RelationLinkEntity entity = service.getById(id);

		return new ResponseEntity<RelationLinkEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/relation/links", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RelationLinkEntity> create(@RequestBody RelationLinkEntity enity)
			throws RecordNotFoundException {
		RelationLinkEntity updated = service.createOrUpdate(enity);
		return new ResponseEntity<RelationLinkEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/relation/links", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody RelationLinkEntity enity) throws RecordNotFoundException {
		service.createOrUpdate(enity);
	}

	@DeleteMapping("/relation/links/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}