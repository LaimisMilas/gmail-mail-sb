package lt.gmail.mail.sender.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gmail_api_setting")
public class GmailAPISettingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "client_id")
	private String clientId;
	
	@Column(name = "default_config")
	private boolean defaultConfig;
	
	@Column(name = "client_secret")
	private String clientSecret;
	
	@Column(name = "redirect_uri")
	private String redirectUri;
	
	@Column(name = "application_name")
	private String applicationName;
	
	@Column(name = "access_token_uri")
	private String accessTokenUri;
	
	@Column(name = "user_authorization_uri")
	private String userAuthorizationUri;
	
	@Column(name = "client_authentication_scheme")
	private String clientAuthenticationScheme;
	
	@Column(name = "scope")
	private String scope;

	@Column(name = "prefer_token_info")
	private String preferTokenInfo;
	
	@OneToOne(targetEntity = UserEntity.class)
	private UserEntity user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public String getAccessTokenUri() {
		return accessTokenUri;
	}
	public void setAccessTokenUri(String accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}
	public String getUserAuthorizationUri() {
		return userAuthorizationUri;
	}
	public void setUserAuthorizationUri(String userAuthorizationUri) {
		this.userAuthorizationUri = userAuthorizationUri;
	}
	public String getClientAuthenticationScheme() {
		return clientAuthenticationScheme;
	}
	public void setClientAuthenticationScheme(String clientAuthenticationScheme) {
		this.clientAuthenticationScheme = clientAuthenticationScheme;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getPreferTokenInfo() {
		return preferTokenInfo;
	}
	public void setPreferTokenInfo(String preferTokenInfo) {
		this.preferTokenInfo = preferTokenInfo;
	}
	public boolean isDefaultConfig() {
		return defaultConfig;
	}
	public void setDefaultConfig(boolean defaultConfig) {
		this.defaultConfig = defaultConfig;
	}
}
