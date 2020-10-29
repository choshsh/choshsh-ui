package com.doms.api.ctrl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doms.api.dto.EgressionSearch;
import com.doms.api.svc.EgressionSvc;

@Controller
public class EgressionCtrl {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EgressionSvc egressionSvc;

	@ResponseBody
	@PostMapping("/api/getEgressionList")
	HashMap<String, Object> getEgressionList(@RequestBody EgressionSearch egressionSearch) throws Exception {
		return egressionSvc.getEgressionList(egressionSearch);

	}

	@ResponseBody
	@PostMapping("/api/getEgressionChart")
	HashMap<String, Object> getEgressionChart(@RequestBody EgressionSearch egressionSearch) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("now", egressionSvc.getNow());
		hm.put("getEgressionObject", egressionSvc.getEgressionObject());
		hm.put("getEgressionChartThisDay", egressionSvc.getEgressionChartThisDay());
		return hm;
	}

	@PostMapping("/api/egressionExcel")
	public void serverExcel(HttpServletRequest req, HttpServletResponse res, EgressionSearch egressionSearch) {
		try {
			egressionSvc.getEgressionExcel(req, res, egressionSearch);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

}
