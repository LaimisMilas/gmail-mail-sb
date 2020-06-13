package lt.gmail.mail.sender.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
public class AppSecurityContext implements SecurityContextRepository {

	@Autowired
	AppAuthentication appAuthentication;
	
	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		System.out.println("**** AppSecurityContext.loadContext");
		HttpServletRequest request = requestResponseHolder.getRequest();
		
		System.out.println("***** request.hashCode " + request.hashCode());
		System.out.println("***** request.getHeader(Authorization) " + request.getHeader("Authorization"));
		System.out.println("***** request.getHeader(Set-Cookie) " + request.getHeader("Set-Cookie"));
		System.out.println("***** request.getServletPath " + request.getServletPath());
		System.out.println("***** request.getMethod " + request.getMethod());
		
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			System.out.println("***** authorizationHeader true");
			String stringToken = authorizationHeader.substring(7);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					stringToken, stringToken);
			Authentication auth = this.appAuthentication.authenticate(authenticationToken);
			System.out.println("***** auth.getName(): " + auth.getName());
			securityContext = new SecurityContextImpl(auth);
			System.out.println("***** securityContext.hashCode " + securityContext.hashCode());
		} else if(request.getMethod().equals("OPTIONS")){
			System.out.println("***** authorizationHeader false");
			System.out.println("***** request.getMethod " + request.getMethod());
			if(securityContext == null) {
				securityContext = new SecurityContextImpl();
			}
			
		} else {
			System.out.println("***** authorizationHeader false");
			Authentication authentication = null;
			securityContext = new SecurityContextImpl(authentication);
			System.out.println("***** securityContext.hashCode " + securityContext.hashCode());
		}
		
		return securityContext;
	}

	private SecurityContext securityContext;

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("**** AppSecurityContext.saveContext");
		System.out.println("***** context.hashCode " + context.hashCode());
		System.out.println("***** securityContext.hashCode " + context.hashCode());
		System.out.println("***** request.hashCode " + request.hashCode());
		System.out.println("***** request.getHeader(Authorization) " + request.getHeader("Authorization"));
		System.out.println("***** request.getHeader(Set-Cookie) " + request.getHeader("Set-Cookie"));
		System.out.println("***** request.getServletPath " + request.getServletPath());
		System.out.println("***** response.getHeader(Set-Cookie) " + response.getHeader("Set-Cookie"));
		
		if (context != null && context.getAuthentication() != null) {
			Authentication auth = context.getAuthentication();
			if(auth != null) {
				System.out.println("***** context auth.getName(): " + auth.getName());
							
			}
		}

		if (securityContext != null && securityContext.getAuthentication() != null) {
			Authentication auth = securityContext.getAuthentication();
			if(auth != null) {
				System.out.println("***** context auth.getName(): " + auth.getName());
							
			}
		}

		if (context != null) {
			securityContext = context;
		}
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		System.out.println("**** AppSecurityContext.containsContext");
		System.out.println("***** request.hashCode " + request.hashCode());
		System.out.println("***** request.getHeader(Authorization) " + request.getHeader("Authorization"));
		System.out.println("***** request.getHeader(Set-Cookie) " + request.getHeader("Set-Cookie"));
		System.out.println("***** request.getServletPath " + request.getServletPath());
		
		boolean result = false;
		
		if(securityContext != null) {
			result = true;
		}
		
		System.out.println("**** AppSecurityContext return " + result);
		
		return false;
	}
}
