package lt.gmail.mail.sender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.CompanyInfoEntity;
import lt.gmail.mail.sender.service.CompanyInfoService;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api")
public class CompanyInfoController {
	
	@Autowired
	CompanyInfoService service;

	@RequestMapping("/company/info")
	public ResponseEntity<List<CompanyInfoEntity>> getAll() {
		List<CompanyInfoEntity> list = service.getAll();

		return new ResponseEntity<List<CompanyInfoEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping("/company/info/search/email/{email}")
	public ResponseEntity<List<CompanyInfoEntity>> searchEmail(@PathVariable("email") String email) {
		List<CompanyInfoEntity> list = service.searchByEmail(email);
		return new ResponseEntity<List<CompanyInfoEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping("/company/info/search/address/{address}")
	public ResponseEntity<List<CompanyInfoEntity>> searchAddress(@PathVariable("address") String address) {
		List<CompanyInfoEntity> list = service.searchByAddress(address);
		return new ResponseEntity<List<CompanyInfoEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping("/company/info/search/rawData/{text}")
	public ResponseEntity<List<CompanyInfoEntity>> searchInRawData(@PathVariable("text") String text) {
		List<CompanyInfoEntity> list = service.searchInRawData(text);
		return new ResponseEntity<List<CompanyInfoEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping("/company/info/{id}")
	public ResponseEntity<CompanyInfoEntity> getById(@PathVariable("id") Long id) throws RecordNotFoundException {
		CompanyInfoEntity entity = service.getById(id);

		return new ResponseEntity<CompanyInfoEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, value="/company/info", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanyInfoEntity> createOrUpdate(@RequestBody CompanyInfoEntity entity)
			throws RecordNotFoundException {
		CompanyInfoEntity updated = service.createOrUpdate(entity);
		return new ResponseEntity<CompanyInfoEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	} 
	
	@RequestMapping(method=RequestMethod.PUT, value="/company/info", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody CompanyInfoEntity entity) throws RecordNotFoundException {
		service.createOrUpdate(entity);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/company/info/{id}")
	public HttpStatus deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteById(id);
		return HttpStatus.FORBIDDEN;
	}

}
