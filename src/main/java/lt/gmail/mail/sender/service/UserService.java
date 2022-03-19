package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.UserEntity;
import lt.gmail.mail.sender.repository.UserRepository;
import lt.gmail.mail.sender.security.JwtTokenProvider;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	AuthenticationManager authenticationManager;

	public List<UserEntity> getAll() {
		List<UserEntity> list = repository.findAll();

		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList<UserEntity>();
		}
	}

	public UserEntity getById(Long id) throws RecordNotFoundException {
		Optional<UserEntity> employee = repository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No User record exist for given id");
		}
	}

	public UserEntity createOrUpdate(UserEntity entity) throws RecordNotFoundException {
		Optional<UserEntity> rep = repository.findById(entity.getId());
		UserEntity result = null;

		if (rep.isPresent()) {

			UserEntity newEntity = rep.get();
			newEntity.setEnabled(entity.isEnabled());
			newEntity.setRoles(entity.getRoles());
			newEntity.setToken(entity.getToken());
			if(!entity.getPassword().equals(newEntity.getPassword())) {
				String newPassword = passwordEncoder.encode(entity.getPassword());
				newEntity.setPassword(newPassword);	
			}
			newEntity.setUserName(entity.getUserName());
			newEntity.setEmail(entity.getEmail());
			newEntity.setAddress(entity.getAddress());
			newEntity.setPhoneNumber(entity.getPhoneNumber());
			result = repository.save(newEntity);
		} else {

			String newPassword = passwordEncoder.encode(entity.getPassword());
			entity.setPassword(newPassword);

			result = repository.save(entity);
		}

		return result;
	}

	public void deleteById(Long id) throws RecordNotFoundException {
		Optional<UserEntity> rep = repository.findById(id);

		if (rep.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No User record exist for given id");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity ud = repository.findByUserName(username);
		return ud;
	}

	public String login(String username, String password) throws Exception {		
	/*	try{
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					username, password);
			authenticationManager.authenticate(authenticationToken);
				
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		*/
		final UserDetails userDetails = this.loadUserByUsername(username);  		
		
		UserEntity userEntity = repository.findByUserName(userDetails.getUsername());
		
		final String jwt = tokenProvider.createToken(userEntity);
		
		return jwt;
	};

	public UserEntity getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = securityContext.getAuthentication().getName();
		return repository.findByUserName(username);
	}
}