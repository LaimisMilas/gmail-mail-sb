package lt.gmail.mail.sender.web;

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
import org.springframework.web.context.request.RequestContextHolder;
import lt.gmail.mail.sender.security.AuthRequest;
import lt.gmail.mail.sender.security.AuthResponse;
import lt.gmail.mail.sender.security.JwtTokenProvider;
import lt.gmail.mail.sender.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService service;
	
	@Autowired
	JwtTokenProvider tokenProvider;

	@RequestMapping(method = RequestMethod.POST, value = "/users/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
		System.out.println("**** AuthController.login");
		AuthResponse authResponse = new AuthResponse();
		String token = service.login(authRequest.getUsername(), authRequest.getPassword());
		authResponse.setToken(token);
		authResponse.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
		return new ResponseEntity<AuthResponse>(authResponse, new HttpHeaders(), HttpStatus.OK);
	}

}
