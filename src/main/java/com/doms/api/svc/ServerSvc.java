package com.doms.api.svc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doms.api.dto.ServerChangeDTO;
import com.doms.api.dto.ServerChartDTO;
import com.doms.api.dto.ServerDTO;
import com.doms.api.dto.ServerSearch;
import com.doms.api.repo.ServerRepo;
import com.doms.api.util.ChartUtil;
import com.doms.api.util.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServerSvc {
	@Autowired
	private ServerRepo serverRepo;

	@Autowired
	private ExcelUtil exu;

	@Autowired
	private ChartUtil cu;

	public List<ServerDTO> getServerList() throws Exception {
		return serverRepo.getServerList();
	}

	public List<ServerDTO> getServerListAll(ServerSearch serverSearch) throws Exception {
		return serverRepo.getServerListAll(serverSearch);
	}

	public List<ServerChangeDTO> getServerChange(ServerSearch serverSearch) throws Exception {
		return serverRepo.getServerChange(serverSearch);
	}

	public List<ServerDTO> getServerListAdd() throws Exception {
		ServerSearch serverSearch = new ServerSearch();
		serverSearch.setCurStDate("thisMonthMade");
		serverSearch.setOrderBy("SL02");
		List<ServerDTO> serverList = serverRepo.getServerList(serverSearch);
		return serverList;
	}

	public List<ServerDTO> getServerListRemove() throws Exception {
		ServerSearch serverSearch = new ServerSearch();
		serverSearch.setCurStDate("thisMonthDel");
		serverSearch.setOrderBy("SL02");
		List<ServerDTO> serverList = serverRepo.getServerListAll(serverSearch);
		return serverList;
	}

	public HashMap<String, Object> getServerListAddOrRemove() throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("made", this.getServerListAdd());
		hm.put("del", this.getServerListRemove());
		return hm;
	}

	public List<ServerChartDTO> getServerChartThisMonth(ServerSearch serverSearch) throws Exception {
		return serverRepo.getServerChartThisMonth(serverSearch);
	}

	public List<Integer> getServerChartYear(ServerSearch serverSearch) throws SQLException {
		List<Integer> result = cu.dataPerYear(serverRepo.getServerChartAll(serverSearch));
		List<Integer> delChart = cu.dataPerYear(serverRepo.getServerChartDel(serverSearch));

		for (int i = 0; i < delChart.size(); i++) {
			result.set(i, result.get(i) - delChart.get(i));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public void getServerExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String[][] dKey = { { "ciNo", "관리번호" }, { "locationNm", "사업장" }, { "nickname", "업무명" }, { "astNm", "호스트명" },
				{ "oprNm", "운영 상태" }, { "serverUsage", "용도" }, { "serverIp", "서비스 IP" }, { "serverOsLine", "OS 계열" },
				{ "serverOs", "OS" }, { "serverDbms", "DBMS" }, { "serverMakerNm", "제조사" }, { "ciModel", "모델명" },
				{ "serialNo", "시리얼번호" }, { "spec", "스펙" }, { "mngAgree", "유지보수" }, { "locationDetail", "설치장소" },
				{ "mngDeptNm", "관리 부서" }, { "mngEmplNm", "관리 담당자" }, { "curStDate", "설치일자" } };
		String[] shArr = { "본사", "구미", "기타" };

		exu.setWbNm("물리서버_리스트");
		for (String s : shArr) {
			exu.addSheet(s);
			/* 헤더 */
			exu.addRow();
			for (int i = 0; i < dKey.length; i++) {
				exu.addCell(i, dKey[i][1]);
			}
			exu.setFilter();
			exu.getSh().createFreezePane(5, 1);
		}

		/* 바디 */
		ServerSearch serverSearch = new ServerSearch();
		serverSearch.setOrderBy("SL01");
		ObjectMapper mapper = new ObjectMapper();
		for (ServerDTO o : serverRepo.getServerList(serverSearch)) {
			exu.setSh(exu.getWb()
					.getSheet(o.getLocation().startsWith("S") ? "본사" : o.getLocation().startsWith("G") ? "구미" : "기타"));
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
}
