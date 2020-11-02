package com.doms.api.svc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doms.api.dto.VmChartDTO;
import com.doms.api.dto.VmDTO;
import com.doms.api.dto.VmSearch;
import com.doms.api.repo.VmRepo;
import com.doms.api.util.ChartUtil;
import com.doms.api.util.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VmSvc {
	@Autowired
	private VmRepo vmRepo;

	@Autowired
	private ExcelUtil exu;

	@Autowired
	private ChartUtil cu;

	public int getVmCount(VmSearch vmSearch) throws Exception {
		return vmRepo.getVmCount(vmSearch);
	}

	public VmDTO getVm(VmSearch vmSearch) throws Exception {
		if (!vmSearch.getVmNo().isEmpty()) {
			return vmRepo.getVm(vmSearch);
		} else {
			return new VmDTO();
		}
	}

	public List<VmDTO> getVmList() throws Exception {
		return vmRepo.getVmList();
	}

	public List<VmDTO> getVmListAll(VmSearch vmSearch) throws Exception {
		return vmRepo.getVmListAll(vmSearch);
	}

	public HashMap<String, Object> getVmCdList(String[] vmCdNmArr) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		for (String item : vmCdNmArr) {
			hm.put(item, vmRepo.getVmCdList(item));
		}
		return hm;
	}

	public List<HashMap<String, Object>> getCountPerOsDetailTotal() throws Exception {
		return vmRepo.getCountPerOsDetailTotal();
	}

	public int vmSave(VmDTO vmDTO) throws Exception {
		if (this.getVmCount(new VmSearch(vmDTO.getVmNo())) > 0) {
			return vmRepo.updateVm(vmDTO);
		} else {
			return vmRepo.insertVm(vmDTO);
		}
	}

	@SuppressWarnings("unchecked")
	public void getVmExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String[][] dKey = { { "vmClusterLocationNm", "사업장" }, { "vmClusterMaker", "장비" }, { "vmNickname", "업무명" },
				{ "vmNm", "VM명" }, { "vmUsageNm", "용도" }, { "vmPowerOn", "전원" }, { "vmIp", "서비스 IP" },
				{ "vmCpuCore", "CPU" }, { "vmMemory", "메모리" }, { "vmDisk", "디스크" }, { "vmOsLine", "OS 계열" },
				{ "vmOsDetail", "OS 상세" }, { "vmUseEmpl", "운영 담당자" }, { "vmMngEmpl", "관리 담당자" },
				{ "vmClusterNm", "클러스터" }, { "vmRegDate", "생성일자" }, { "vmDelYn", "삭제여부" }, { "vmDelDate", "삭제일자" },
				{ "vmComment", "Comment" } };
		String[] shArr = { "본사", "구미", "삭제" };

		exu.setWbNm("가상서버_리스트");

		for (String s : shArr) {
			exu.addSheet(s);
			/* 헤더 */
			exu.addRow();
			for (int i = 0; i < dKey.length; i++) {
				exu.addCell(i, dKey[i][1]);
			}
			exu.setFilter();
			/* (열, 행) 틀 고정 */
			exu.getSh().createFreezePane(5, 1);
		}

		/* 바디 */
		VmSearch vmSearch = new VmSearch();
		vmSearch.setOrderBy("VLA01");
		ObjectMapper mapper = new ObjectMapper();
		for (VmDTO o : vmRepo.getVmListAll(vmSearch)) {
			exu.setSh(exu.getWb().getSheet(!o.getVmDelYn().equalsIgnoreCase("N") ? "삭제"
					: o.getVmClusterLocation().startsWith("S") ? "본사" : "구미"));
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

	public VmChartDTO getVmChartMonthly() throws Exception {
		return vmRepo.getVmChartMonthly();
	}

	public List<VmChartDTO> getVmDatastore() throws Exception {
		return vmRepo.getVmDatastore();
	}

	public List<VmChartDTO> getVmHost() throws Exception {
		return vmRepo.getVmHost();
	}

	public int delVm(VmSearch vmSearch) {
		return vmRepo.delVm(vmSearch);
	}

	public List<Integer> getVmChartYear(VmSearch vmSearch) throws SQLException {
		List<Integer> result = cu.dataPerYear(vmRepo.getVmChartTotal(vmSearch));
		List<Integer> delChart = cu.delDataPerYear(vmRepo.getVmChartTotalDel(vmSearch));
		for (int i = 0; i < delChart.size(); i++) {
			result.set(i, result.get(i) - delChart.get(i));
		}
		return result;
	}

}
