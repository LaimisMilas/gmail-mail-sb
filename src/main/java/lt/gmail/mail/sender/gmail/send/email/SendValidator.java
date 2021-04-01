package lt.gmail.mail.sender.gmail.send.email;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.gmail.mail.sender.model.SendRegEntity;
import lt.gmail.mail.sender.repository.GmailUsubscribeRepository;
import lt.gmail.mail.sender.repository.SendRegRepository;
import lt.gmail.mail.sender.utils.StaticList;

@Service
public class SendValidator {
	
	@Autowired
	GmailUsubscribeRepository usubscribeRepository;
	
	@Autowired
    SendRegRepository sendRegRepository;
    

	public boolean isSended(String companyCode, String logs) {

		if (companyCode == null || companyCode.isEmpty() ) {
			throw new NullPointerException("companyCode value is: " + companyCode);
		}
		
		if (logs == null || logs.isEmpty()) {
			throw new NullPointerException("logs value is: " + logs);
		}

		boolean isSend = false;

		List<SendRegEntity> sendLogs = sendRegRepository.selectAllByCompanyCode(companyCode);

		for (SendRegEntity item : sendLogs) {

			String log = (String) item.getLogs();

			if (log.contains(logs)) {

				isSend = true;
			}

		}

		return isSend;
	}
	
	public boolean isSendedByMail(String email, String key) {

		if (email == null || email.isEmpty() ) {
			throw new NullPointerException("email value is: " + email);
		}
		
		if (key == null || key.isEmpty() ) {
			throw new NullPointerException("key value is: " + key);
		}

		boolean isSend = false;

		List<SendRegEntity> sendLogs = sendRegRepository.seachInLogs(email);

		for (SendRegEntity item : sendLogs) {

			String log = (String) item.getLogs();

			if (log.contains(key)) {

				isSend = true;
			}

		}

		return isSend;
	}

	public boolean isInBlackList(String companyCode) {
		return StaticList.ignorCompanys.contains(companyCode);
	}
	
	public boolean isInEmailBlackList(String email) {
		return usubscribeRepository.findByEmail(email) == null ? false : true;
	}
	
	public boolean isInFreandList(String email) {
		boolean emails1 = true;
		boolean result = false;
		if(!emails1) {
			result = false;
		}
		return result;
	}

	public boolean isInGmailLimits(Long compaignId, int limit) {
		
		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    	long time = new Date().getTime() - MILLIS_PER_DAY;
    	Date before24 = new Date();
    	before24.setTime(time);
		
		List<SendRegEntity> list = sendRegRepository.allInInterval(compaignId, before24, new Date());
		return list.size() >= limit;
	}

}
