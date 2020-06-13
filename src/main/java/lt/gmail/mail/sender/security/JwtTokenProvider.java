package lt.gmail.mail.sender.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lt.gmail.mail.sender.model.UserEntity;

@Component
public class JwtTokenProvider {

  @Value("qweqwqq234234ww3452323423525325qweqwqq234234ww3452323423525325qweqwqq234234ww3452323423525325qweqwqq234234ww3452323423525325")
  private String jwtSecret;

  @Value("60")
  private int jwtExpiration;

  public String generateToken(UserEntity user) {
	System.out.println("************ JwtTokenProvider.generateToken");
		
    Map<String, Object> claims = new HashMap<>();
    List<String> roles = new ArrayList<String>();
    user.getRoles().forEach(item -> roles.add(item.getRole()));
    claims.put("roles", roles);
    claims.put("enable", user.isEnabled());
    return generateToken(claims, user.getUserName());
  }

  public String generateToken(Map<String, Object> claims, String username) {
	  System.out.println("************ JwtTokenProvider.generateToken");
		
	  Date createdDate = new Date();
    Date expiryDate = new Date(createdDate.getTime() + (jwtExpiration * 60) * 1000);

    return Jwts.builder()
      .setClaims(claims)
      .setSubject(username)
      .setIssuedAt(createdDate)
      .setExpiration(expiryDate)
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();
  }

  public Claims getAllClaimsFromToken(String token, String jwtSecret) {
	  System.out.println("************ JwtTokenProvider.getAllClaimsFromToken");
		
	  return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
  }

  public String getUsernameFromToken(String token, String jwtSecret) {
	  System.out.println("************ JwtTokenProvider.getUsernameFromToken");
		
	  Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken, String jwtSecret) {
	  System.out.println("************ JwtTokenProvider.validateToken");

	  try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (Exception ex) {
    	ex.printStackTrace();
    }
    return false;
  }
}
