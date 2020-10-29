package com.doms.api.repo;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.doms.api.dto.ServerChangeDTO;
import com.doms.api.dto.ServerChartDTO;
import com.doms.api.dto.ServerDTO;
import com.doms.api.dto.ServerSearch;

@Repository
public class ServerRepo {
	protected static final String NAMESPACE = "com.doms.server.";

	@Autowired
	@Qualifier("db2SqlSessionTemplate")
	private SqlSession sqlSession;

	public List<ServerDTO> getServerList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getServerList");
	}

	public List<ServerDTO> getServerList(ServerSearch serverSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getServerList", serverSearch);
	}

	public List<ServerDTO> getServerListAll(ServerSearch serverSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getServerListAll", serverSearch);
	}

	public List<Integer> getServerChartAll(ServerSearch serverSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getServerChartAll", serverSearch);
	}

	public List<Integer> getServerChartDel(ServerSearch serverSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getServerChartDel", serverSearch);
	}

	public List<ServerChangeDTO> getServerChange(ServerSearch serverSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getServerChange", serverSearch);
	}

	public List<ServerChartDTO> getServerChartThisMonth(ServerSearch serverSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getServerChartThisMonth", serverSearch);
	}
}