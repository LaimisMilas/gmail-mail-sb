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
import lt.gmail.mail.sender.model.SendRegEntity;
import lt.gmail.mail.sender.service.SendRegService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SendRegController {
	
	@Autowired
	SendRegService service;

	@RequestMapping("/send/regs")
	public ResponseEntity<List<SendRegEntity>> getAll() {
		List<SendRegEntity> list = service.getAll();

		return new ResponseEntity<List<SendRegEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping("/send/regs/{id}")
	public ResponseEntity<SendRegEntity> getById(@PathVariable Long id) throws RecordNotFoundException {
		SendRegEntity entity = service.getById(id);

		return new ResponseEntity<SendRegEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping("/send/regs/search/{logKey}")
	public ResponseEntity<List<SendRegEntity>> getByKey(@PathVariable String logKey) throws RecordNotFoundException {
		List<SendRegEntity> entity = service.seachInLogs(logKey);

		return new ResponseEntity<List<SendRegEntity>>(entity, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/send/regs", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void create(@RequestBody SendRegEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/send/regs", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody SendRegEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/send/regs/{id}")
	public HttpStatus deleteById(@PathVariable Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}