package lt.gmail.mail.sender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.gmail.mail.sender.exception.RecordNotFoundException;
import lt.gmail.mail.sender.model.CompanyInfoEntity;
import lt.gmail.mail.sender.model.GmailCampaignEntity;
import lt.gmail.mail.sender.service.CompaignManageService;
import lt.gmail.mail.sender.service.GmailCampaignService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class GmailMailSenderController {

	@Autowired
	GmailCampaignService service;

	@Autowired
	CompaignManageService cManageService;

	@RequestMapping("/do/send/init/{id}")
	public void doSendEmail(@PathVariable("id") Long id) {

		GmailCampaignEntity entity = null;
		try {
			entity = service.getById(id);
			if (entity != null) {
				
				List<CompanyInfoEntity> rlist = entity.getRecipientList().getRecipients();
				
				cManageService.runCompaign(entity, rlist);
			}
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/do/send/start/{id}")
	public String doSendStart(@PathVariable("id") Long id) {
		return cManageService.startSend(id);
	}
	
	@RequestMapping("/do/send/status/{id}")
	public String doSendStatus(@PathVariable("id") Long id) {
		return cManageService.getStatus(id);
	}
	
	@RequestMapping("/send/manager/status")
	public String senderManagerStatus() {
		return cManageService.senderManagerStatus();
	}
	
	@RequestMapping("/do/send/stop/{id}")
	public String doSendStop(@PathVariable("id") Long id) {
		return cManageService.stopSend(id);
	}
	
	@RequestMapping("/set/send/limit/{id}/{limit}")
	public String setLimit(@PathVariable("id") Long id, @PathVariable("limit") int limit) {
		return cManageService.setLimit(id, limit);
	}
	
	@RequestMapping("/set/time/to/wait/{id}/{timeToWait}")
	public String setTimeToWait(@PathVariable("id") Long id, @PathVariable("timeToWait") int timeToWait) {
		return cManageService.setTimeToWait(id, timeToWait);
	}

}