package lt.gmail.mail.sender.web;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import lt.gmail.mail.sender.model.GmailAPISettingEntity;
import lt.gmail.mail.sender.service.CompaignManageService;
import lt.gmail.mail.sender.service.GmailApiSettingService;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/auth")
public class GMailAuthController {

	private String applicationName = "Quickstart";
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private GoogleClientSecrets clientSecrets;
	private GoogleAuthorizationCodeFlow flow;
	private Credential credential;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String scope;
	
	@Autowired
	CompaignManageService cms;
	@Autowired
	GmailApiSettingService gmailApiSettingService;

	@RequestMapping(value = "/login/gmail/{id}", method = RequestMethod.GET)
	public RedirectView googleConnectionStatus(@PathVariable("id") Long id) throws Exception {
		GmailAPISettingEntity config = gmailApiSettingService.getByUserId(id);
		
		if(config != null) {
			this.clientId = config.getClientId();
			this.clientSecret = config.getClientSecret();
			this.redirectUri = config.getRedirectUri();
			this.applicationName = config.getApplicationName();
			this.scope = config.getScope();
		}
		
		return new RedirectView(authorize());
	}

	@RequestMapping(value = "/login/gmailCallback", method = RequestMethod.GET, params = "code")
	public ResponseEntity<String> oauth2Callback(@RequestParam(value = "code") String code) {

		try {
			TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
			credential = flow.createAndStoreCredential(response, "userID");

			cms.setService(new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
					.setApplicationName(applicationName).build());
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("", HttpStatus.OK);
	}

	private String authorize() throws Exception {
		AuthorizationCodeRequestUrl authorizationUrl;
		if (flow == null) {
			Details web = new Details();
			web.setClientId(clientId);
			web.setClientSecret(clientSecret);
			clientSecrets = new GoogleClientSecrets().setWeb(web);
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
					Collections.singleton(scope)).build();
		}
		authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri);
		return authorizationUrl.build();
	}
}
