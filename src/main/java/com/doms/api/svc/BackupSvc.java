package com.doms.api.svc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doms.api.dto.BackupChartDTO;
import com.doms.api.dto.BackupDTO;
import com.doms.api.dto.BackupSearch;
import com.doms.api.repo.BackupRepo;
import com.doms.api.util.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BackupSvc {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BackupRepo backupRepo;

	public HashMap<String, Object> getBackupList(BackupSearch nmcSearch) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			this.setBackupDate(nmcSearch);
			this.setLocation(nmcSearch);
			hm.put("nmcSearch", nmcSearch);
			hm.put("list", backupRepo.getBackupList(nmcSearch));
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return hm;
	}

	public HashMap<String, Object> getBackupListFull(BackupSearch nmcSearch) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			this.setBackupDate(nmcSearch);
			this.setLocation(nmcSearch);
			hm.put("nmcSearch", nmcSearch);
			hm.put("list", backupRepo.getBackupListFull(nmcSearch));
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return hm;
	}

	public List<BackupDTO> getSaveSetNameList(BackupSearch nmcSearch) throws Exception {
		return backupRepo.getSaveSetNameList(nmcSearch);
	}

	public HashMap<String, Object> getClientChange(BackupSearch nmcSearch) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			this.setLocation(nmcSearch);
			hm.put("nmcSearch", nmcSearch);
			hm.put("add", backupRepo.getClientChange(nmcSearch));
			String getStdDate = nmcSearch.getStdDate();
			nmcSearch.setStdDate(nmcSearch.getTargetDate());
			nmcSearch.setTargetDate(getStdDate);
			hm.put("drop", backupRepo.getClientChange(nmcSearch));
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return hm;
	}

	@SuppressWarnings("unchecked")
	public void getBackupExcel(HttpServletRequest req, HttpServletResponse res, BackupSearch nmcSearch) {
		String[][] dKey = { { "clientName", "서버명" }, { "groupName", "그룹명" }, { "backupLevel", "레벨" },
				{ "saveSetSize", "백업 크기 (GB)" }, { "workflowStartTime", "시작 일시" }, { "workflowEndTime", "종료 일시" },
				{ "term", "소요 시간 (h/m/s)" }, { "saveSetNameCnt", "백업 대상 개수" } };

		String nlNm = "";
		List<BackupDTO> nlCdList = (List<BackupDTO>) this.getBackupCdList("location").get("location");
		for (BackupDTO o : nlCdList) {
			if (o.getNlCd().equalsIgnoreCase(nmcSearch.getNlCd()))
				nlNm = o.getNlNm();
		}
		String[] shArr = { "백업_리스트_" + nlNm + "_" + nmcSearch.getWorkflowStartDate().replace("/", ".") + "~"
				+ nmcSearch.getWorkflowEndDate().replace("/", ".") };
		ExcelUtil exu = new ExcelUtil("백업_리스트_" + nlNm);
		for (String s : shArr) {
			exu.addSheet(s);
			/* 헤더 */
			exu.addRow();
			for (int i = 0; i < dKey.length; i++) {
				exu.addCell(i, dKey[i][1]);
			}
			exu.setFilter();
			exu.getSh().createFreezePane(3, 1);
		}

		/* 바디 */
		nmcSearch.setWorkflowStartDate(this.convertDate(nmcSearch.getWorkflowStartDate()));
		nmcSearch.setWorkflowEndDate(this.convertDate(nmcSearch.getWorkflowEndDate()));
		List<BackupDTO> list = (List<BackupDTO>) this.getBackupList(nmcSearch).get("list");
		list.forEach(o -> {
			o.setWorkflowStartTime(o.getWorkflowStartTime().substring(0, 16));
			o.setWorkflowEndTime(o.getWorkflowEndTime().substring(0, 16));
		});
		ObjectMapper mapper = new ObjectMapper();
		for (BackupDTO o : list) {
			exu.addRow();
			HashMap<String, Object> m = mapper.convertValue(o, HashMap.class);
			for (int i = 0; i < dKey.length; i++) {
				exu.addCell(i, m.get(dKey[i][0]) == null ? "-" : m.get(dKey[i][0]).toString());
			}
		}
		/* 너비 조정 */
		IntStream.range(0, shArr.length).forEach(i -> {
			exu.setWidth(exu.getWb().getSheetAt(i).getRow(0), i);
		});
		/* 1행 필터 생성 */
		exu.setFilter();

		/* 다운로드 */
		exu.download(req, res);
	}

	@SuppressWarnings("unchecked")
	public void getBackupExcelFull(HttpServletRequest req, HttpServletResponse res, BackupSearch nmcSearch) {
		String[][] dKey = { { "clientName", "서버명" }, { "policyName", "등급" }, { "groupName", "그룹명" },
				{ "saveSetName", "백업 대상" }, { "saveSetSize", "백업 크기 (GB)" }, };

		String nlNm = "";
		List<BackupDTO> nlCdList = (List<BackupDTO>) this.getBackupCdList("location").get("location");
		for (BackupDTO o : nlCdList) {
			if (o.getNlCd().equalsIgnoreCase(nmcSearch.getNlCd()))
				nlNm = o.getNlNm();
		}
		String[] shArr = { "백업_리스트_" + nlNm + "_" + nmcSearch.getWorkflowStartDate().replace("/", ".") + "~"
				+ nmcSearch.getWorkflowEndDate().replace("/", ".") };
		ExcelUtil exu = new ExcelUtil("백업_리스트_" + nlNm);
		for (String s : shArr) {
			exu.addSheet(s);
			/* 헤더 */
			exu.addRow();
			for (int i = 0; i < dKey.length; i++) {
				exu.addCell(i, dKey[i][1]);
			}
			exu.setFilter();
			exu.getSh().createFreezePane(3, 1);
		}

		/* 바디 */
		nmcSearch.setWorkflowStartDate(this.convertDate(nmcSearch.getWorkflowStartDate()));
		nmcSearch.setWorkflowEndDate(this.convertDate(nmcSearch.getWorkflowEndDate()));
		List<BackupDTO> list = (List<BackupDTO>) this.getBackupListFull(nmcSearch).get("list");
		/*
		 * list.forEach(o -> {
		 * o.setWorkflowStartTime(o.getWorkflowStartTime().substring(0, 16));
		 * o.setWorkflowEndTime(o.getWorkflowEndTime().substring(0, 16)); });
		 */
		ObjectMapper mapper = new ObjectMapper();
		for (BackupDTO o : list) {
			exu.addRow();
			HashMap<String, Object> m = mapper.convertValue(o, HashMap.class);
			for (int i = 0; i < dKey.length; i++) {
				exu.addCell(i, m.get(dKey[i][0]) == null ? "-" : m.get(dKey[i][0]).toString());
			}
		}
		/* 너비 조정 */
		IntStream.range(0, shArr.length).forEach(i -> {
			exu.setWidth(exu.getWb().getSheetAt(i).getRow(0), i);
		});
		/* 1행 필터 생성 */
		exu.setFilter();

		/* 다운로드 */
		exu.download(req, res);
	}

	public HashMap<String, Object> getBackupCdList(String[] cdNmArr) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		for (String item : cdNmArr) {
			try {
				hm.put(item, backupRepo.getBackupCdList(item));
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}
		}
		return hm;
	}

	public HashMap<String, Object> getBackupCdList(String cdNmArr) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			hm.put(cdNmArr, backupRepo.getBackupCdList(cdNmArr));
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return hm;
	}

	public void setBackupDate(BackupSearch nmcSearch) {
		try {
			String now = backupRepo.getNow();
			nmcSearch.setWorkflowStartDate(
					nmcSearch.getWorkflowStartDate() == null ? now : nmcSearch.getWorkflowStartDate());
			nmcSearch.setWorkflowEndDate(nmcSearch.getWorkflowEndDate() == null ? now : nmcSearch.getWorkflowEndDate());
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	public String getNow() {
		String result = "";
		try {
			result = backupRepo.getNow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void setLocation(BackupSearch nmcSearch) {
		nmcSearch.setNlCd(nmcSearch.getNlCd() == null ? "S" : nmcSearch.getNlCd());
	}

	public List<BackupChartDTO> getBackupChart(BackupSearch nmcSearch) {
		List<BackupChartDTO> list = null;
		try {
			this.setLocation(nmcSearch);
			list = backupRepo.getBackupChart(nmcSearch);
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return list;
	}

	public List<BackupChartDTO> getBackupChartThisMonth(BackupSearch nmcSearch) {
		List<BackupChartDTO> list = null;
		try {
			this.setLocation(nmcSearch);
			list = backupRepo.getBackupChartThisMonth(nmcSearch);
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return list;
	}

	private String convertDate(String date) {
		return date.substring(5, 7) + "/" + date.substring(8, 10) + "/" + date.substring(0, 4);
	}

	public String getMailNotiContent(String nlCd, String nlNm) {
		String result = "";

		int failCnt = 0;
		ArrayList<String> failList = new ArrayList<>();

		BackupSearch backupSearch = new BackupSearch();
		backupSearch.setNlCd(nlCd);

		@SuppressWarnings("unchecked")
		List<BackupDTO> list = (List<BackupDTO>) this.getBackupList(backupSearch).get("list");
		for (BackupDTO item : list) {
			if (item.getWorkflowStatus().equalsIgnoreCase("failed")) {
				failCnt++;
				failList.add(item.getGroupName());
			}
		}

		result += "\n\n[" + nlNm + "]";
		result += "\n전체: " + list.size();
		result += " (성공: " + (list.size() - failCnt);
		result += " / 실패: " + failCnt;
		result += ")";

		if (failCnt > 0) {
			result += "\n실패 대상:";
			for (String s : failList) {
				result += s + ", ";
			}
		}

		return result;
	}

}
