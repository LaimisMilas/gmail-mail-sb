package lt.gmail.mail.sender.gmail.api;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.Gmail.Users;
import com.google.api.services.gmail.Gmail.Users.GetProfile;
import com.google.api.services.gmail.Gmail.Users.Settings;
import com.google.api.services.gmail.Gmail.Users.Settings.SendAs;
import com.google.api.services.gmail.Gmail.Users.Settings.SendAs.Get;
import com.google.api.services.gmail.Gmail.Users.Settings.SendAs.List;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GmailAPIHelper {

	/**
	 * Create draft email.
	 *
	 * @param service      an authorized Gmail API instance
	 * @param userId       user's email address. The special value "me" can be used
	 *                     to indicate the authenticated user
	 * @param emailContent the MimeMessage used as email within the draft
	 * @return the created draft
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static Draft createDraft(Gmail service, String userId, MimeMessage emailContent)
			throws MessagingException, IOException {
		Message message = createMessageWithEmail(emailContent);
		Draft draft = new Draft();
		draft.setMessage(message);
		draft = service.users().drafts().create(userId, draft).execute();

		System.out.println("Draft id: " + draft.getId());
		System.out.println(draft.toPrettyString());
		return draft;
	}

	/**
	 * Send an email from the user's mailbox to its recipient.
	 *
	 * @param service      Authorized Gmail API instance.
	 * @param userId       User's email address. The special value "me" can be used
	 *                     to indicate the authenticated user.
	 * @param emailContent Email to be sent.
	 * @return The sent message
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static Message sendMessage(Gmail service, String userId, MimeMessage emailContent)
			throws MessagingException, IOException {
		Message message = createMessageWithEmail(emailContent);
		message = service.users().messages().send(userId, message).execute();

		System.out.println("Message id: " + message.getId());
		System.out.println(message.toPrettyString());
		return message;
	}
	
	public static Message sendMessage(Gmail service, String userId, Message message)
			throws MessagingException, IOException {
		GetProfile up = service.users().getProfile(userId);
		SendAs r = service.users().settings().sendAs();
		if(!userId.equals(up.get("userId"))) {
			// System.exit(0);
		} else {
			//
		}
		message = service.users().messages().send("me", message).execute();
		System.out.println("Message id: " + message.getId());
		System.out.println(message.toPrettyString());
		return message;
	}	
	

	/**
	 * Create a message from an email.
	 *
	 * @param emailContent Email to be set to raw of message
	 * @return a message containing a base64url encoded email
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}

	/**
	 * Create a MimeMessage using the parameters provided.
	 *
	 * @param to       email address of the receiver
	 * @param from     email address of the sender, the mailbox account
	 * @param subject  subject of the email
	 * @param bodyText body text of the email
	 * @return the MimeMessage to be used to send email
	 * @throws MessagingException
	 */
	public static MimeMessage createEmail(String to, String from, String subject, String bodyText)
			throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);
		email.setText(bodyText);
		return email;
	}

	/**
	 * Create a MimeMessage using the parameters provided.
	 *
	 * @param to       Email address of the receiver.
	 * @param from     Email address of the sender, the mailbox account.
	 * @param subject  Subject of the email.
	 * @param bodyText Body text of the email.
	 * @param file     Path to the file to be attached.
	 * @return MimeMessage to be used to send email.
	 * @throws MessagingException
	 */
	public static MimeMessage createEmailWithAttachment(String to, String from, String subject, String bodyText,
			File file) throws MessagingException, IOException {
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(bodyText, "text/plain");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		mimeBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);

		mimeBodyPart.setDataHandler(new DataHandler(source));
		mimeBodyPart.setFileName(file.getName());

		multipart.addBodyPart(mimeBodyPart);
		email.setContent(multipart);

		return email;
	}

	public static Message createHTMLEmail(String from, String to, String subject, String html, File file) {

		Message resultMessage = null;
		
		try {

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			MimeMessage email = new MimeMessage(session);

			email.setFrom(new InternetAddress(from));
			email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			email.setSubject(subject);

//			Multipart multiPart = new MimeMultipart("alternative");
			Multipart multiPart = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(html, "text/html; charset=utf-8");
			multiPart.addBodyPart(htmlPart);
			
			if(file != null) {
				MimeBodyPart attachementPart = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				attachementPart.setDataHandler(new DataHandler(source));
				attachementPart.setFileName(file.getName());
				multiPart.addBodyPart(attachementPart);
			}

			email.setContent(multiPart);
			
			

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			email.writeTo(output);
			
			String rawEmail = Base64.encodeBase64URLSafeString(output.toByteArray());

			resultMessage = new Message();
			resultMessage.setRaw(rawEmail);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMessage;

	}

}