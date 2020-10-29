package com.doms.api.ctrl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doms.api.dto.BackupDTO;
import com.doms.api.dto.BackupSearch;
import com.doms.api.svc.BackupSvc;

@Controller
public class BackupCtrl {

	@Autowired
	BackupSvc backupSvc;

	@ResponseBody
	@PostMapping("/api/backups")
	HashMap<String, Object> backupList(@RequestBody BackupSearch backupSearch) {
		HashMap<String, Object> hm = new HashMap<>();
		String[] cdNmArr = { "location" };
		hm.put("getBackupCdList", backupSvc.getBackupCdList(cdNmArr));
		hm.put("getBackupList", backupSvc.getBackupList(backupSearch));
		return hm;
	}

	@PostMapping("/api/backupExcel")
	public void backupExcel(HttpServletRequest req, HttpServletResponse res, BackupSearch backupSearch) {
		backupSvc.getBackupExcel(req, res, backupSearch);
	}

	@PostMapping("/api/backupExcelFull")
	public void backupExcelFull(HttpServletRequest req, HttpServletResponse res, BackupSearch backupSearch) {
		backupSvc.getBackupExcelFull(req, res, backupSearch);
	}

	@ResponseBody
	@PostMapping("/api/getSaveSetList")
	List<BackupDTO> saveSetList(@RequestBody BackupSearch backupSearch) throws Exception {
		return backupSvc.getSaveSetNameList(backupSearch);
	}

}
