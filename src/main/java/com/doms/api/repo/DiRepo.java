package com.doms.api.repo;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doms.api.dto.DiDTO;
import com.doms.api.dto.DiSearch;
import com.doms.api.dto.DieDTO;

@Repository
public class DiRepo {
	protected static final String NAMESPACE = "com.doms.di.";

	@Autowired
	private SqlSession sqlSession;

	public int getDiCount(DiDTO diDTO) {
		return sqlSession.selectOne(NAMESPACE + "getDiCount", diDTO);
	}

	public int getMnYn(String diDate) {
		return sqlSession.selectOne(NAMESPACE + "getMnYn", diDate);
	}

	public int getAftnYn(String diDate) {
		return sqlSession.selectOne(NAMESPACE + "getAftnYn", diDate);
	}

	public List<DiDTO> getDiList(DiSearch diSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getDiList", diSearch);
	}

	public List<DieDTO> getDieList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getDieList");
	}

	public int insertDi(DiDTO diDTO) {
		return sqlSession.insert(NAMESPACE + "insertDi", diDTO);
	}

	public int updateDi(DiDTO diDTO) {
		return sqlSession.update(NAMESPACE + "updateDi", diDTO);
	}

	public int insertDie(DieDTO dieDTO) {
		return sqlSession.insert(NAMESPACE + "insertDie", dieDTO);
	}

	public int deleteDie(DiDTO diDTO) {
		return sqlSession.delete(NAMESPACE + "deleteDie", diDTO);
	}

}