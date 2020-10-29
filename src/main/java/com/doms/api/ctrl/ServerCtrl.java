package com.doms.api.ctrl;

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

import com.doms.api.dto.ServerChangeDTO;
import com.doms.api.dto.ServerChartDTO;
import com.doms.api.dto.ServerDTO;
import com.doms.api.dto.ServerSearch;
import com.doms.api.svc.ServerSvc;

@Controller
public class ServerCtrl {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ServerSvc serverSvc;

	@ResponseBody
	@GetMapping("/api/servers")
	List<ServerDTO> servers() throws Exception {
		return serverSvc.getServerList();
	}

	@ResponseBody
	@PostMapping("/api/serversAll")
	List<ServerDTO> serversAll(@RequestBody ServerSearch serverSearch) throws Exception {
		return serverSvc.getServerListAll(serverSearch);
	}

	@ResponseBody
	@PostMapping("/api/serverChange")
	List<ServerChangeDTO> serverChange(@RequestBody ServerSearch serverSearch) throws Exception {
		return serverSvc.getServerChange(serverSearch);
	}

	@ResponseBody
	@PostMapping("/api/getServerChartThisMonth")
	List<ServerChartDTO> getServerChartThisMonth(@RequestBody ServerSearch serverSearch) throws Exception {
		return serverSvc.getServerChartThisMonth(serverSearch);
	}

	@ResponseBody
	@PostMapping("/api/getServerChartYear")
	List<Integer> getServerChartYear(@RequestBody ServerSearch serverSearch, HttpServletRequest req) throws Exception {
		return serverSvc.getServerChartYear(serverSearch);
	}

	@PostMapping("/api/serverExcel")
	public void serverExcel(HttpServletRequest req, HttpServletResponse res) {
		try {
			serverSvc.getServerExcel(req, res);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

}
