package com.doms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.doms.api.dto.BackupDTO;
import com.doms.api.dto.MailDTO;
import com.doms.api.svc.BackupSvc;
import com.doms.api.svc.MailSvc;

@Controller
@Component
public class TestCtrl {
	@Autowired
	private BackupSvc backupSvc;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Date now = new Date();
	private String strDate = sdf.format(now);
	@Autowired
	private MailSvc mailSvc;
	private String msg = "";
	private String title = "";

	@SuppressWarnings("unchecked")
	@GetMapping("/api/test")
	public void test() {
		try {
			String[] mailToArr = { "choshsh@torayamk.com" };

			title = "[본사 백업 알림] " + strDate;
			msg += strDate + " 기준,";
			msg += "\n전일 19:00 ~ 금일 09:00 간 Networker 백업 실행 상태는 아래와 같습니다.\n";

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
