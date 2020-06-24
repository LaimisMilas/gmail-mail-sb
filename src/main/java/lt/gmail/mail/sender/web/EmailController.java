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
import lt.gmail.mail.sender.model.EmailEntity;
import lt.gmail.mail.sender.service.EmailService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    EmailService service;

    @RequestMapping(method = RequestMethod.GET, value = "/emails")
    public ResponseEntity<List<EmailEntity>> getAll() {
        List<EmailEntity> list = service.getAll();

        return new ResponseEntity<List<EmailEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/emails/{id}")
    public ResponseEntity<EmailEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
    EmailEntity entity = service.getById(id);

    return new ResponseEntity<EmailEntity>(entity, new HttpHeaders(), HttpStatus.OK);
}

@RequestMapping(method = RequestMethod.POST, value = "/emails", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<EmailEntity> create(@RequestBody EmailEntity enity) throws RecordNotFoundException {
    EmailEntity updated = service.createOrUpdate(enity);
    return new ResponseEntity<EmailEntity>(updated, new HttpHeaders(), HttpStatus.OK);
}

@RequestMapping(method = RequestMethod.PUT, value = "/emails", consumes = MediaType.APPLICATION_JSON_VALUE)
public void update(@RequestBody EmailEntity enity) throws RecordNotFoundException {
    service.createOrUpdate(enity);
}

@DeleteMapping("/emails/{id}")
public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
    service.deleteById(id);
    return HttpStatus.FORBIDDEN;
}

}