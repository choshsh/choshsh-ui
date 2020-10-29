package com.doms.api.ctrl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doms.api.dto.UserDTO;
import com.doms.api.svc.MailSvc;
import com.doms.api.svc.UserSvc;

@Controller
public class MainCtrl {
	@Autowired
	UserSvc userSvc;
	@Autowired
	MailSvc mailSvc;

	@Value("${linkedURL.monitoring}")
	private String monitoring;

	@Value("${linkedURL.repo}")
	private String repo;

	@Value("${linkedURL.gitlab}")
	private String gitlab;

	/* 최초 접근 시 vuejs 화면을 띄운다 */
	@GetMapping({ "", "/" })
	String main() {
		return "index.html";
	}

	@ResponseBody
	@PostMapping("/api/login")
	HashMap<String, Object> login(@RequestBody UserDTO userDTO) throws Exception {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("check", userSvc.checkLogin(userDTO));
		if (Integer.parseInt(hm.get("check").toString()) > 0)
			hm.put("getUser", userSvc.getUser(userDTO.getUserId()));
		return hm;
	}

	@ResponseBody
	@PostMapping("/api/linkedURL")
	HashMap<String, String> getLinkedURL() throws Exception {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("monitoring", monitoring);
		hm.put("repo", repo);
		hm.put("gitlab", gitlab);
		return hm;
	}
}
