package com.doms.api.svc;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doms.api.dto.EgressionDTO;
import com.doms.api.dto.EgressionSearch;
import com.doms.api.repo.EgressionRepo;
import com.doms.api.util.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EgressionSvc {
	@Autowired
	private EgressionRepo ergressionRepo;

	public String getNow() throws Exception {
		return ergressionRepo.getNow();
	}

	public void setEgDate(EgressionSearch egressionSearch) throws Exception {
		String now = ergressionRepo.getNow02();
		if (egressionSearch.getSDate() == null) {
			String tmp = now.substring(0, 3) + "01" + now.substring(5);
			egressionSearch.setSDate(tmp);
		}
		if (egressionSearch.getEDate() == null) {
			egressionSearch.setEDate(now);
		}
	}

	public HashMap<String, Object> getEgressionList(EgressionSearch egressionSearch) throws Exception {
		HashMap<String, Object> hm = new HashMap<>();
		this.setEgDate(egressionSearch);
		hm.put("egressionSearch", egressionSearch);
		hm.put("list", ergressionRepo.getEgressionList(egressionSearch));
		return hm;
	}

	@SuppressWarnings("unchecked")
	public void getEgressionExcel(HttpServletRequest req, HttpServletResponse res, EgressionSearch egressionSearch)
			throws Exception {
		String[][] dKey = { { "teamNm", "부서" }, { "mbrNm", "이름" }, { "egDate", "기간" }, { "egLocDtl", "고객사" },
				{ "egObjectDtl", "직출 사유" }, { "siteWork", "직출 구분" }, { "egStatusNm", "현재 상태" } };
		String[] shArr = { "정보_1팀" };

		ExcelUtil exu = new ExcelUtil("외출_리스트");
		for (String s : shArr) {
			exu.addSheet(s);
			/* 헤더 */
			exu.addRow();
			for (int i = 0; i < dKey.length; i++) {
				exu.addCell(i, dKey[i][1]);
			}
			exu.setFilter();
			exu.getSh().createFreezePane(2, 1);
		}

		/* 바디 */
		egressionSearch.setOrderBy("ELO01");
		ObjectMapper mapper = new ObjectMapper();
		for (EgressionDTO o : ergressionRepo.getEgressionList(egressionSearch)) {
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

	public List<EgressionDTO> getEgressionChartThisDay() throws Exception {
		return ergressionRepo.getEgressionChartThisDay();
	}

	public List<EgressionDTO> getEgressionObject() throws Exception {
		return ergressionRepo.getEgressionObject();
	}

}
