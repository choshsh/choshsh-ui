package com.doms.api.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doms.api.dto.BoardDTO;
import com.doms.api.dto.BoardSearch;
import com.doms.api.svc.BoardSvc;

@Controller
public class BoardCtrl {
	protected static final String PREFIX = "/board";

	@Autowired
	private BoardSvc boardSvc;

	@ResponseBody
	@PostMapping("/api/board/getList")
	List<BoardDTO> boardList(@RequestBody BoardSearch boardSearch) throws Exception {
		return boardSvc.getBoardList(boardSearch);
	}

	@ResponseBody
	@PostMapping("/api/board/getInfo")
	BoardDTO boardInfo(@RequestBody BoardSearch boardSearch) throws Exception {
		return boardSvc.getBoard(boardSearch);
	}

	@ResponseBody
	@PostMapping("/api/boardForm/boardSave")
	int boardSave(@RequestBody BoardDTO boardDTO) throws Exception {
		return boardSvc.boardSave(boardDTO);
	}

	@ResponseBody
	@PostMapping("/api/boardDel")
	int delVm(@RequestBody BoardSearch boardSearch) {
		return boardSvc.delBoard(boardSearch);
	}
}
