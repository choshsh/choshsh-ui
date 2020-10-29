package com.doms.api.repo;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doms.api.dto.BoardDTO;
import com.doms.api.dto.BoardSearch;

@Repository
public class BoardRepo {
	protected static final String NAMESPACE = "com.doms.board.";

	@Autowired
	private SqlSession sqlSession;

	public int getBoardCount(BoardSearch boardSearch) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getBoardCount", boardSearch);
	}
	public int getBoardCount(BoardDTO boardDTO) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getBoardCount", boardDTO);
	}

	public BoardDTO getBoard(BoardSearch boardSearch) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getBoard", boardSearch);
	}

	public List<BoardDTO> getBoardList(BoardSearch boardSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getBoardList", boardSearch);
	}

	public int insertBoard(BoardDTO boardDTO) {
		return sqlSession.insert(NAMESPACE + "insertBoard", boardDTO);
	}

	public int updateBoard(BoardDTO boardDTO) {
		return sqlSession.update(NAMESPACE + "updateBoard", boardDTO);
	}

	public int delBoard(BoardSearch boardSearch) {
		return sqlSession.update(NAMESPACE + "delBoard", boardSearch);
	}
}