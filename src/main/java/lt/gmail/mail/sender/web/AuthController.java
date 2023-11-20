package lt.gmail.mail.sender.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.UserEntity;
import lt.gmail.mail.sender.model.UserRoleEntity;
import lt.gmail.mail.sender.security.AuthRequest;
import lt.gmail.mail.sender.security.AuthResponse;
import lt.gmail.mail.sender.service.UserRoleService;
import lt.gmail.mail.sender.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService service;
	
	@Autowired
	UserRoleService roleService;

	@RequestMapping(method = RequestMethod.POST, value = "/users/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) throws Exception {
		AuthResponse authResponse = new AuthResponse();
		String token = service.login(authRequest.getUsername(), authRequest.getPassword());
		authResponse.setToken(token);
		return new ResponseEntity<AuthResponse>(authResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/users/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> create(@RequestBody UserEntity enity) throws RecordNotFoundException {
		List<UserRoleEntity> roles = new ArrayList<UserRoleEntity>();
		roles.add(roleService.getByName("ADMIN"));
		enity.setEnabled(true);
		UserEntity updated = service.createOrUpdate(enity);
		return new ResponseEntity<UserEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}
}
