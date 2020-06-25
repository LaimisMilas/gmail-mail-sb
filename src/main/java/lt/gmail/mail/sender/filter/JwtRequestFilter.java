package lt.gmail.mail.sender.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import lt.gmail.mail.sender.security.JwtTokenProvider;
import lt.gmail.mail.sender.service.UserService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String userName = null;
		String jwt = null;
		Claims claims = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			
				jwt = authorizationHeader.substring(7);
				userName = jwtTokenProvider.getUsernameFromToken(jwt);
				claims = jwtTokenProvider.getAllClaimsFromToken(jwt);
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userService.loadUserByUsername(userName);
			if(jwtTokenProvider.validateToken(jwt, userDetails)) {
				List<String> roles = claims.get("roles", List.class);
				
			      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			    		  userName,
			        null,
			        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
			      );     
			      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
