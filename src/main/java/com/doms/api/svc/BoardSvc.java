package com.doms.api.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doms.api.dto.BoardDTO;
import com.doms.api.dto.BoardSearch;
import com.doms.api.repo.BoardRepo;

@Service
public class BoardSvc {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardRepo boardRepo;

	public int getBoardCount(BoardSearch boardSearch) throws Exception {
		return boardRepo.getBoardCount(boardSearch);
	}

	public int getBoardCount(BoardDTO boardDTO) throws Exception {
		return boardRepo.getBoardCount(boardDTO);
	}

	public BoardDTO getBoard(BoardSearch boardSearch) throws Exception {
		if (boardSearch.getBdSeq() > 0) {
			return boardRepo.getBoard(boardSearch);
		} else {
			return new BoardDTO();
		}
	}

	public List<BoardDTO> getBoardList(BoardSearch boardSearch) throws Exception {
		return boardRepo.getBoardList(boardSearch);
	}

	public int boardSave(BoardDTO boardDTO) {
		int result = 0;

		try {
			if (this.getBoardCount(boardDTO) > 0) {
				result = boardRepo.updateBoard(boardDTO);
			} else {
				result = boardRepo.insertBoard(boardDTO);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return result;
	}

	public int delBoard(BoardSearch boardSearch) {
		return boardSearch.getBdSeq() > 0 ? boardRepo.delBoard(boardSearch) : 0;
	}
}
