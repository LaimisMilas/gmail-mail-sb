package lt.gmail.mail.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.UserEntity;
import lt.gmail.mail.sender.repository.UserRepository;
import lt.gmail.mail.sender.security.AppAuthentication;
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
	AppAuthentication appAuthentication;

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
		System.out.println("******** UserService.loadUserByUsername");
		UserEntity ud = repository.findByUserName(username);
		return ud;
	}

	public String login(String username, String password) {
		System.out.println("******** UserService.login");
		String result = null;
		UserEntity user = repository.findByUserName(username);
		System.out.println("******** login is founded: " + user == null);
		boolean matche = true;
		matche = passwordEncoder.matches(password, user.getPassword());
		if (matche) {
			System.out.println("******** password matches");
			result = tokenProvider.generateToken(user);
			System.out.println("******** generateToken: " + result != null);
			if(result != null && !result.isEmpty()) {
				SecurityContext securityContext = SecurityContextHolder.getContext();
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						result, result);
				Authentication auth = appAuthentication.authenticate(authenticationToken);
				securityContext.setAuthentication(auth);
			}
		}
		return result;
	}

	public UserEntity getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = securityContext.getAuthentication().getName();
		return repository.findByUserName(username);
	}
}