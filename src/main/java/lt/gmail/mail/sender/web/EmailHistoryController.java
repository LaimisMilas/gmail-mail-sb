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
import lt.gmail.mail.sender.model.EmailHistoryEntity;
import lt.gmail.mail.sender.service.EmailHistoryService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EmailHistoryController {

    @Autowired
    EmailHistoryService service;

    @RequestMapping(method = RequestMethod.GET, value = "/email/historys")
    public ResponseEntity<List<EmailHistoryEntity>> getAll() {
        List<EmailHistoryEntity> list = service.getAll();

        return new ResponseEntity<List<EmailHistoryEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/email/historys/{id}")
    public ResponseEntity<EmailHistoryEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
    EmailHistoryEntity entity = service.getById(id);

    return new ResponseEntity<EmailHistoryEntity>(entity, new HttpHeaders(), HttpStatus.OK);
}

@RequestMapping(method = RequestMethod.POST, value = "/email/historys", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<EmailHistoryEntity> create(@RequestBody EmailHistoryEntity enity) throws RecordNotFoundException {
    EmailHistoryEntity updated = service.createOrUpdate(enity);
    return new ResponseEntity<EmailHistoryEntity>(updated, new HttpHeaders(), HttpStatus.OK);
}

@RequestMapping(method = RequestMethod.PUT, value = "/email/historys", consumes = MediaType.APPLICATION_JSON_VALUE)
public void update(@RequestBody EmailHistoryEntity enity) throws RecordNotFoundException {
    service.createOrUpdate(enity);
}

@DeleteMapping("/email/historys/{id}")
public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
    service.deleteById(id);
    return HttpStatus.FORBIDDEN;
}

}