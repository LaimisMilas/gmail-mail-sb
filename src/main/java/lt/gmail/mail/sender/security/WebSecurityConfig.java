package lt.gmail.mail.sender.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lt.gmail.mail.sender.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AppAuthentication appAuthentication;

	@Autowired
	AppSecurityContext appSecurityContext;

	@Autowired
	UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("WebSecurityConfig.passwordEncoder");
		return new BCryptPasswordEncoder(11);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
	    .antMatchers("/api/**").access("hasAnyAuthority('ADMIN')")
	    .antMatchers("/auth/**").anonymous()
	    .antMatchers("/**").anonymous()
	    .anyRequest().fullyAuthenticated()
		.and()
		.userDetailsService(userService)
		.authenticationProvider(appAuthentication)
		.securityContext()
		.securityContextRepository(appSecurityContext)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
		.and()
		.csrf().disable()
		.formLogin().disable();
	}
}