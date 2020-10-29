package com.doms.api.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doms.api.dto.DiDTO;
import com.doms.api.dto.DiSearch;
import com.doms.api.dto.DieDTO;
import com.doms.api.svc.DiSvc;

@Controller
public class DiCtrl {

	@Autowired
	private DiSvc diSvc;

	@ResponseBody
	@PostMapping("/api/dis")
	List<DiDTO> getDiList(@RequestBody DiSearch diSearch) throws Exception {
		return diSvc.getDiList(diSearch);
	}

	@ResponseBody
	@PostMapping("/api/dies")
	List<DieDTO> getDieList() throws Exception {
		return diSvc.getDieList();
	}

	@ResponseBody
	@PostMapping("/api/diSave")
	int diSave(@RequestBody DiDTO diDTO) throws Exception {
		return diSvc.saveDi(diDTO);
	}
}
