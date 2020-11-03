package com.doms.api.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.doms.api.dto.BackupDTO;
import com.doms.api.dto.MailDTO;
import com.doms.api.svc.BackupSvc;
import com.doms.api.svc.DiSvc;
import com.doms.api.svc.MailSvc;

@Component
public class Scheduler {

	@Value("${spring.profiles}")
	private String profiles;

	@Autowired
	private DiSvc diSvc;

	@Autowired
	private MailSvc mailSvc;

	@Autowired
	private BackupSvc backupSvc;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Date now = new Date();
	private String strDate = sdf.format(now);

	private String title = "";
	private String msg = "";

	/*
	 * cron 설정: 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
	 */

	/* 서버실 점검 오전 체크 */
	/* 월~금 10:30에 실행 */
	@Scheduled(cron = "0 30 10 * * 1-5")
	public void diSchedulerMorning() {
		if (profiles.equalsIgnoreCase("prod")) {
			String[] mailToArr = { "choshsh@torayamk.com", "jewoos16@torayamk.com", "shj0322@torayamk.com",
					"deliod085@torayamk.com" };

			if (diSvc.getMnYn(strDate) < 1) {
				title = "[본사 서버실 점검] " + strDate + " 오전 점검 요망";
				msg = strDate + " 오전에 서버실 점검이 이루어지지 않았습니다.\n\n점검을 부탁드립니다.";
				for (String s : mailToArr) {
					mailSvc.mailSend(new MailDTO(s, title, msg));
				}
			}
		}
	}

	/* 서버실 점검 오후 체크 */
	/* 월~금 17:00에 실행 */
	@Scheduled(cron = "0 0 17 * * 1-5")
	public void diSchedulerAfternoon() {
		if (profiles.equalsIgnoreCase("prod")) {
			String[] mailToArr = { "choshsh@torayamk.com", "jewoos16@torayamk.com", "shj0322@torayamk.com",
					"deliod085@torayamk.com" };

			if (diSvc.getAftnYn(strDate) < 1) {
				title = "[본사 서버실 점검] " + strDate + " 오후 점검 요망";
				msg = strDate + " 오후에 서버실 점검이 이루어지지 않았습니다.\n\n점검을 부탁드립니다.";
				for (String s : mailToArr) {
					mailSvc.mailSend(new MailDTO(s, title, msg));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 10 * * 1-5")
	public void backupNoti() {
		if (profiles.equalsIgnoreCase("prod")) {
			try {
				String[] mailToArr = { "choshsh@torayamk.com", "shj0322@torayamk.com", "deliod085@torayamk.com",
						"mdhong@torayamk.com" };

				title = "[백업 알림] " + strDate;
				msg += strDate + " 기준,";
				msg += "\n전일 18:00 ~ 금일 10:00 간 Networker 백업 실행 상태는 아래와 같습니다.\n";

				for (BackupDTO item : (List<BackupDTO>) backupSvc.getBackupCdList("location").get("location")) {
					msg += backupSvc.getMailNotiContent(item.getNlCd(), item.getNlNm());
				}

				for (String s : mailToArr) {
					mailSvc.mailSend(new MailDTO(s, title, msg));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
