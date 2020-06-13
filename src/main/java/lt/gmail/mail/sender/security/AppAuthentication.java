package lt.gmail.mail.sender.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class AppAuthentication implements AuthenticationProvider {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

  @Override
  public Authentication authenticate(Authentication authentication) {
	  System.out.println("******** AppAuthentication.authenticate");
	  
    String stringToken = authentication.getCredentials().toString();
    String jwtSecret = "qweqwqq234234ww3452323423525325qweqwqq234234ww3452323423525325qweqwqq234234ww3452323423525325qweqwqq234234ww3452323423525325";
    String username;
    try {
      username = jwtTokenProvider.getUsernameFromToken(stringToken, jwtSecret);
    } catch (Exception e) {
      username = null;
    }
    if (username != null && jwtTokenProvider.validateToken(stringToken, jwtSecret)) {
      Claims claims = jwtTokenProvider.getAllClaimsFromToken(stringToken, jwtSecret);
      List<String> roles = claims.get("roles", List.class);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        username,
        null,
        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
      );
      return authenticationToken;
    } else {
      return null;
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
	  System.out.println("******** AppAuthentication..supports");
	  return Arrays.stream(authentication.getInterfaces())
      .anyMatch(i -> i.getName().equals(Authentication.class.getName()));
  }
}
