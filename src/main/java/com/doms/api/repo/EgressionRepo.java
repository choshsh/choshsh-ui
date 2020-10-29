package com.doms.api.repo;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.doms.api.dto.EgressionDTO;
import com.doms.api.dto.EgressionSearch;

@Repository
public class EgressionRepo {
	protected static final String NAMESPACE = "com.doms.egression.";

	@Autowired
	@Qualifier("db1SqlSessionTemplate")
	private SqlSession sqlSession;

	@Autowired
	@Qualifier("db2SqlSessionTemplate")
	private SqlSession sqlSession2;

	public String getNow() throws SQLException {
		return sqlSession2.selectOne(NAMESPACE + "getNow");
	}

	public String getNow02() throws SQLException {
		return sqlSession2.selectOne(NAMESPACE + "getNow02");
	}

	public List<EgressionDTO> getEgressionList(EgressionSearch egressionSearch) throws SQLException {
		return sqlSession2.selectList(NAMESPACE + "getEgressionList", egressionSearch);
	}

	public List<EgressionDTO> getEgressionChartThisDay() throws SQLException {
		return sqlSession2.selectList(NAMESPACE + "getEgressionChartThisDay");
	}

	public List<EgressionDTO> getEgressionObject() throws SQLException {
		return sqlSession2.selectList(NAMESPACE + "getEgressionObject");
	}
}