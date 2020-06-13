package lt.gmail.mail.sender.gmail.api;

import java.io.File;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

public class GmailAPIImpl {

	private Gmail service;
	public String subject;
	private String emailContent;
	private File attachement;


	public Gmail getService() {
		return this.service;
	}
	
	public void setService(Gmail service) {
		this.service = service;
	}

	public Message send(String sendTo, String sendFrom, String htmlContent) {
		Message result = null;
		try {

			Message content = GmailAPIHelper.createHTMLEmail(sendFrom, sendTo, subject, htmlContent, attachement);

			result = GmailAPIHelper.sendMessage(getService(), sendFrom, content);

			if (result != null && !result.isEmpty() && result.containsKey("id")) {

				System.out.println(result.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
	public File getAttachement() {
		return attachement;
	}

	public void setAttachement(File attachement) {
		this.attachement = attachement;
	}

	public void sleepThread(long time) {

		try {

			Thread.sleep(time);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
