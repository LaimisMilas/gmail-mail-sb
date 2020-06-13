package lt.gmail.mail.sender.security;

public class AuthResponse {

  private String token;
  private String sessionId;
  

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getSessionId() {
	return sessionId;
}

public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}
  
  
}
