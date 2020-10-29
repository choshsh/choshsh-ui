package com.doms.api.ctrl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doms.api.dto.VmDTO;
import com.doms.api.dto.VmSearch;
import com.doms.api.svc.VmSvc;

@Controller
public class VmCtrl {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	VmSvc vmSvc;

	@ResponseBody
	@GetMapping("/api/vms")
	List<VmDTO> vmList() throws Exception {
		return vmSvc.getVmList();
	}

	@ResponseBody
	@PostMapping("/api/vmsAll")
	List<VmDTO> vmListAll(@RequestBody VmSearch vmSearch) throws Exception {
		return vmSvc.getVmListAll(vmSearch);
	}

	@ResponseBody
	@PostMapping("/api/getVm")
	VmDTO getVm(@RequestBody VmSearch vmSearch) throws Exception {
		return vmSvc.getVm(vmSearch);
	}

	@ResponseBody
	@PostMapping("/api/vmForm")
	HashMap<String, Object> vmForm(@RequestBody VmSearch vmSearch) throws Exception {
		HashMap<String, Object> hm = new HashMap<>();
		String[] vmCdNmArr = { "os", "cluster", "usage" };
		hm.put("getVmCdList", vmSvc.getVmCdList(vmCdNmArr));
		hm.put("getVm", vmSvc.getVm(vmSearch));
		return hm;
	}

	@PostMapping("/api/vmExcel")
	public void vmExcel(HttpServletRequest req, HttpServletResponse res) {
		try {
			vmSvc.getVmExcel(req, res);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping("/api/vms/getVmChart")
	HashMap<String, Object> getVmChart() throws Exception {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("getVmChartMonthly", vmSvc.getVmChartMonthly());
		hm.put("getCountPerUsageDetailTotal", vmSvc.getCountPerOsDetailTotal());
		return hm;
	}

	@ResponseBody
	@PostMapping("/api/vms/getVmChartHostDatastore")
	HashMap<String, Object> getVmChartHostDatastore() throws Exception {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("getVmDatastore", vmSvc.getVmDatastore());
		hm.put("getVmHost", vmSvc.getVmHost());
		return hm;
	}

	@ResponseBody
	@PostMapping("/api/getVmChartYear")
	List<Integer> getVmChartYear(@RequestBody VmSearch vmSearch) throws Exception {
		return vmSvc.getVmChartYear(vmSearch);
	}

	@ResponseBody
	@PostMapping("/api/vmSave")
	int vmStore(@RequestBody VmDTO vmDTO) throws Exception {
		return vmSvc.vmSave(vmDTO);
	}

	@ResponseBody
	@PostMapping("/api/delVm")
	int delVm(@RequestBody VmSearch vmSearch) throws Exception {
		return vmSvc.delVm(vmSearch);
	}
}
