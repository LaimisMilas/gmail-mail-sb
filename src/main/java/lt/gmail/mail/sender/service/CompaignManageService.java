package lt.gmail.mail.sender.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;

import lt.gmail.mail.sender.gmail.send.email.SendToCompanysViaGmail;
import lt.gmail.mail.sender.gmail.send.email.SendValidator;
import lt.gmail.mail.sender.model.CompanyInfoEntity;
import lt.gmail.mail.sender.model.GmailCampaignEntity;
import lt.gmail.mail.sender.repository.SendRegRepository;

@Service
public class CompaignManageService {

	@Autowired
	GmailCampaignService gmailCampaignService;
	@Autowired
	private SendRegRepository sendRegRepository;
	@Autowired
	private SendValidator sendValidator;

	private Gmail gmailService;
	private Map<Long, String> itemsCFLogs = new HashMap<Long, String>();
	private Map<Long, CompletableFuture<SendToCompanysViaGmail>> itemsCF = new HashMap<Long, CompletableFuture<SendToCompanysViaGmail>>();

	public void runCompaign(GmailCampaignEntity compaign, List<CompanyInfoEntity> rlist) {

		SendToCompanysViaGmail mailSender = new SendToCompanysViaGmail();
		mailSender.setSubjectLine(compaign.getSubjectLine());
		List<CompanyInfoEntity> companyInfos = null;
		if (rlist.size() > 0) {
			companyInfos = rlist;
			System.out.println(companyInfos.get(0).getEmail());
		}
		mailSender.setCompanyInfos(companyInfos);
		mailSender.setEmailContent(compaign.getGmailHTML().getHtmlContent());
		mailSender.setLogKey(compaign.getLogKey());
		mailSender.setService(gmailService);
		mailSender.setSendRegRepository(sendRegRepository);
		mailSender.setSendValidator(sendValidator);
		mailSender.setStop(false);
		mailSender.setRunTest(true);
		mailSender.setLimit(1000);
		runCompaignAsync(compaign.getId(), mailSender);
	}

	@Async
	public void runCompaignAsync(Long id, SendToCompanysViaGmail mailSender) {
		CompletableFuture<SendToCompanysViaGmail> completionFuture = CompletableFuture.completedFuture(mailSender);
		if (itemsCF.containsKey(id)) {
			SendToCompanysViaGmail sender;
			try {
				sender = itemsCF.get(id).get();
				sender.setStop(true);
				itemsCF.get(id).cancel(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		itemsCF.put(id, completionFuture);
		try {
			itemsCF.get(id).get().run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public String getStatus(Long id) {
		String result = "";
		try {

			if (itemsCF.get(id) != null) {
				SendToCompanysViaGmail sender = itemsCF.get(id).get();
				result = result + "Status: " + sender.getStatus();
				result =  result + ", Counter: " + sender.getCounter();
				result =  result + ", TimeToWait: " + sender.getTimeToWait();
				result =  result + ", LogKey: " + sender.getLogKey();
				result =  result + ", Limit: " + sender.getLimit();
				result =  result + ", CompanyInfos.size: " + sender.getCompanyInfos().size();
				result =  result + ", DoSendEmail: " + sender.isDoSendEmail();
				result =  result + ", DoStoreReg: " + sender.isDoStoreReg();
				result =  result + ", DoTimeToWait: " + sender.isDoTimeToWait();
				result =  result + ", LookByCompanyCode: " + sender.isLookByCompanyCode();
				result =  result + ", LookByEmail: " + sender.isLookByEmail();
				result =  result + ", LookByEmailInFreandList: " + sender.isLookByEmailInFreandList();
				result =  result + ", LookByEmailInLogs: " + sender.isLookByEmailInLogs();
				result =  result + ", LookByLogKey: " + sender.isLookByLogKey();
				result =  result + ", LookByStatus: " + sender.isLookByStatus();
				result =  result + ", RunTest: " + sender.isRunTest();
				result =  result + ", Stop: " + sender.isStop();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String startSend(Long id) {
		String result = "";
		try {

			if (itemsCF.get(id) != null) {
				SendToCompanysViaGmail sender = itemsCF.get(id).get();
				sender.setStop(false);
				sender.setRunTest(false);
				sender.run();
				result = sender.getStatus();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String stopSend(Long id) {
		String result = "";
		try {

			if (itemsCF.get(id) != null) {
				SendToCompanysViaGmail sender = itemsCF.get(id).get();
				sender.setStop(true);
				sender.setRunTest(false);
				result = sender.getStatus();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setService(Gmail gmail) {
		this.gmailService = gmail;
	}

	public String senderManagerStatus() {
		String result = "";
		result = result + "Compaigns itemsCF size: " + itemsCF.size();
		result = result + ", Compaigns itemsCF keySet: " + itemsCF.keySet();
		result = result + ", GmailService: " + (gmailService == null ? "null": "not null");
		if(gmailService != null) {
			result = result + "GmailService ApplicationName: " + gmailService.getApplicationName();
		}
		for (Entry<Long, String> entry : itemsCFLogs.entrySet()) {
			entry.getValue();
		}	
		return result;
	}
}