package lt.gmail.mail.sender.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lt.gmail.mail.sender.service.UserService;

/** Spring Security configuration. */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig2 {

	private final AppAuthentication appAuthentication = new AppAuthentication();
	private final AppSecurityContext appSecurityContext = new AppSecurityContext();
	@Autowired
	UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("**** SecurityConfig.passwordEncoder");
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
		System.out.println("**** SecurityConfig.webSecurityConfigurerAdapter");
		return new WebSecurityConfigurerAdapter() {
			@Override
			protected void configure(HttpSecurity http) throws Exception {
				System.out.println("**** WebSecurityConfigurerAdapter.configure");
				http.httpBasic().disable().formLogin().disable().logout().disable().csrf().disable()
						// .anonymous()
						// .disable()
						.userDetailsService(userService).authenticationProvider(appAuthentication).securityContext()
						.securityContextRepository(appSecurityContext).and()
						.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and()
						.authorizeRequests()
						.antMatchers(HttpMethod.POST).permitAll().antMatchers("/api/users/register", "/api/users/login")
						.permitAll();
			}
		};
	}
}
