package lt.gmail.mail.sender.gmail.send.email;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import lt.gmail.mail.sender.gmail.api.GmailAPIImpl;
import lt.gmail.mail.sender.model.CompanyInfoEntity;
import lt.gmail.mail.sender.model.SendRegEntity;
import lt.gmail.mail.sender.repository.SendRegRepository;
import lt.gmail.mail.sender.utils.Utils;

public class SendToCompanysViaGmail implements Runnable {

	SendRegRepository sendRegRepository;
	SendValidator sendValidator;
	Gmail service;
	List<CompanyInfoEntity> companyInfos;
	Long compaignId;
	String sendFrom = "me";
	String subjectLine;
	String emailContent;
	String logKey;
	File attachement;
	int timeToWait = 7000;
	int counter = 0;
	int limit;
	String status = "idl";
	boolean stop = false;
	boolean runTest = true;
	boolean lookByEmail = false;
	boolean lookByEmailInLogs = true;
	boolean lookByEmailInFreandList = true;
	boolean lookByLogKey = false;
	boolean lookByStatus = false;
	boolean lookByCompanyCode = false;
	boolean lookIsInBlackList = true;
	boolean lookIsEmailInBlackList = true;
	boolean lookForLimits = true;
	boolean doTimeToWait = true;
	boolean doSendEmail = true;
	boolean doStoreReg = true;

	public void run() {

		try {
			
			status = "Running";
			
			while(runTest) {
				status = "Runnig test";
				System.out.println("Test is Running");
				Utils.sleepThread(timeToWait);
				if(stop) {
					status = "Stoped";
					break;
				}
			}

			GmailAPIImpl sendGmailAPI = new GmailAPIImpl();
			sendGmailAPI.setService(service);
			sendGmailAPI.setSubject(subjectLine);
			sendGmailAPI.setEmailContent(emailContent);
			sendGmailAPI.setAttachement(attachement);

			for (int a = 0; a < this.companyInfos.size(); a++) {
				
				//limit = companyInfos.size();
				
				if(stop) {
					status = "Stoped";
					break;
				}
				
				CompanyInfoEntity company = null;

				Long companyInfoId = null;
				String companyCode = null;
				String companyInfoEmail = null;
				Message response = null;

				try {

					company = this.companyInfos.get(a);

					companyInfoId = company.getId();
					companyCode = company.getCompanyCode();
					companyInfoEmail = company.getEmail();

					if (lookForLimits && counter > limit) {
						status = "Stoped by limit";
						break;
					}

					if (lookIsInBlackList && isInBlackList(companyCode)) {
						System.out.println("*** Rejected isInBlackList id: " + companyInfoId + " companyCode:" + companyCode);
						continue;
					}

					if (lookIsEmailInBlackList && isInEmailBlackList(companyInfoEmail)) {
						System.out.println("*** Rejected isInEmailBlackList id: " + companyInfoId + " companyCode:" + companyCode);
						continue;
					}

					if (companyInfoEmail != null && companyInfoEmail.trim().length() <= 0) {
						System.out.println("*** Rejected email <= 0 id: " + companyInfoId + " email:" + companyInfoEmail);
						continue;
					}

					if (lookByCompanyCode && (companyCode == null || companyCode.trim().length() <= 0)) {
						System.out.println("*** Rejected companyCode <= 0 id: " + companyInfoId + " companyCode:" + companyCode);
						continue;
					}

					if (lookByLogKey && isSended(companyCode, logKey)) {
						System.out.println("*** Rejected isSended id: " + companyInfoId + " logKey:" + logKey);
						continue;
					}

					if (lookByEmail && isSended(companyCode, companyInfoEmail)) {
						System.out.println("*** Rejected isSended id: " + companyInfoId + " Email:" + companyInfoEmail);
						continue;
					}

					if (lookByEmailInLogs && isSendedByMail(companyInfoEmail, logKey)) {
						System.out.println("*** Rejected isSended id: " + companyInfoId + " Email:" + companyInfoEmail);
						continue;
					}

					if (lookByEmailInFreandList && isInFreandList(companyInfoEmail)) {
						System.out.println("*** Rejected isInFreandList id: " + companyInfoId + " Email:" + companyInfoEmail);
						continue;
					}

					if (doTimeToWait) {
						Utils.sleepThread(timeToWait);
					}

					if (doSendEmail) {
						sendGmailAPI.subject = this.subjectLine;
						response = sendGmailAPI.send(companyInfoEmail, sendFrom, this.emailContent);
					}

					System.out.println(
							counter + " Sended to company: " + companyCode + " id: " + companyInfoId + ". Email: " + companyInfoEmail);

					counter++;

					if (doStoreReg) {

						if (response == null) {
							response = new Message();
						}

						SendRegEntity sendReg = new SendRegEntity();
						sendReg.setCompaignId(compaignId);
						sendReg.setCreated(new Date());
						sendReg.setCompanyCode(companyCode);
						sendReg.setLogs(logKey + " " + companyInfoEmail +" companyInfoId "+ companyInfoId);
						sendReg.setMessage(response.toPrettyString());
						sendRegRepository.save(sendReg);
					}

				} catch (Exception e) {
					System.out.println("*** Exception to company: " + companyCode + " id: " + companyInfoId + " Email: " + companyInfoEmail);
					status = "Stoped by error";
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			status = "Stoped by error";
			e.printStackTrace();
		}
	}

	private boolean isSendedByMail(String email, String logKey) {
		return sendValidator.isSendedByMail(email, logKey);
	}

	private boolean isSended(String companyCode, String logs) {
		return sendValidator.isSended(companyCode, logs);
	}

	private boolean isInBlackList(String companyCode) {
		return sendValidator.isInBlackList(companyCode);
	}

	private boolean isInEmailBlackList(String email) {
		return sendValidator.isInEmailBlackList(email);
	}

	private boolean isInFreandList(String email) {
		return sendValidator.isInFreandList(email);
	}

	public SendRegRepository getSendRegRepository() {
		return sendRegRepository;
	}

	public void setSendRegRepository(SendRegRepository sendRegRepository) {
		this.sendRegRepository = sendRegRepository;
	}

	public SendValidator getSendValidator() {
		return sendValidator;
	}

	public void setSendValidator(SendValidator sendValidator) {
		this.sendValidator = sendValidator;
	}

	public Gmail getService() {
		return service;
	}

	public void setService(Gmail service) {
		this.service = service;
	}

	public List<CompanyInfoEntity> getCompanyInfos() {
		return companyInfos;
	}

	public void setCompanyInfos(List<CompanyInfoEntity> companyInfos) {
		this.companyInfos = companyInfos;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public String getSubjectLine() {
		return subjectLine;
	}

	public void setSubjectLine(String subjectLine) {
		this.subjectLine = subjectLine;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}

	public File getAttachement() {
		return attachement;
	}

	public void setAttachement(File attachement) {
		this.attachement = attachement;
	}

	public int getTimeToWait() {
		return timeToWait;
	}

	public void setTimeToWait(int timeToWait) {
		this.timeToWait = timeToWait;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isRunTest() {
		return runTest;
	}

	public void setRunTest(boolean runTest) {
		this.runTest = runTest;
	}

	public boolean isLookByEmail() {
		return lookByEmail;
	}

	public void setLookByEmail(boolean lookByEmail) {
		this.lookByEmail = lookByEmail;
	}

	public boolean isLookByEmailInLogs() {
		return lookByEmailInLogs;
	}

	public void setLookByEmailInLogs(boolean lookByEmailInLogs) {
		this.lookByEmailInLogs = lookByEmailInLogs;
	}

	public boolean isLookByEmailInFreandList() {
		return lookByEmailInFreandList;
	}

	public void setLookByEmailInFreandList(boolean lookByEmailInFreandList) {
		this.lookByEmailInFreandList = lookByEmailInFreandList;
	}

	public boolean isLookByLogKey() {
		return lookByLogKey;
	}

	public void setLookByLogKey(boolean lookByLogKey) {
		this.lookByLogKey = lookByLogKey;
	}

	public boolean isLookByStatus() {
		return lookByStatus;
	}

	public void setLookByStatus(boolean lookByStatus) {
		this.lookByStatus = lookByStatus;
	}

	public boolean isLookByCompanyCode() {
		return lookByCompanyCode;
	}

	public void setLookByCompanyCode(boolean lookByCompanyCode) {
		this.lookByCompanyCode = lookByCompanyCode;
	}

	public boolean isLookIsInBlackList() {
		return lookIsInBlackList;
	}

	public void setLookIsInBlackList(boolean lookIsInBlackList) {
		this.lookIsInBlackList = lookIsInBlackList;
	}

	public boolean isLookIsEmailInBlackList() {
		return lookIsEmailInBlackList;
	}

	public void setLookIsEmailInBlackList(boolean lookIsEmailInBlackList) {
		this.lookIsEmailInBlackList = lookIsEmailInBlackList;
	}

	public boolean isLookForLimits() {
		return lookForLimits;
	}

	public void setLookForLimits(boolean lookForLimits) {
		this.lookForLimits = lookForLimits;
	}

	public boolean isDoTimeToWait() {
		return doTimeToWait;
	}

	public void setDoTimeToWait(boolean doTimeToWait) {
		this.doTimeToWait = doTimeToWait;
	}

	public boolean isDoSendEmail() {
		return doSendEmail;
	}

	public void setDoSendEmail(boolean doSendEmail) {
		this.doSendEmail = doSendEmail;
	}

	public boolean isDoStoreReg() {
		return doStoreReg;
	}

	public void setDoStoreReg(boolean doStoreReg) {
		this.doStoreReg = doStoreReg;
	}

	public Long getCompaignId() {
		return compaignId;
	}

	public void setCompaignId(Long compaignId) {
		this.compaignId = compaignId;
	}
}
