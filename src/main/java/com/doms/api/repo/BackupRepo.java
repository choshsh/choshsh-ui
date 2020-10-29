package com.doms.api.repo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.doms.api.dto.BackupChartDTO;
import com.doms.api.dto.BackupDTO;
import com.doms.api.dto.BackupSearch;

@Repository
public class BackupRepo {
	protected static final String NAMESPACE = "com.doms.backup.";

	// 본사
	@Autowired
	@Qualifier("db1SqlSessionTemplate")
	private SqlSession sqlSession;

	// 본사
	@Autowired
	@Qualifier("db3SqlSessionTemplate")
	private SqlSession sqlSession3;

	// 구미 1
	@Autowired
	@Qualifier("db4SqlSessionTemplate")
	private SqlSession sqlSession4;

	// 구미1 TCK
	@Autowired
	@Qualifier("db5SqlSessionTemplate")
	private SqlSession sqlSession5;

	// 구미3
	@Autowired
	@Qualifier("db6SqlSessionTemplate")
	private SqlSession sqlSession6;

	// 구미 4
	@Autowired
	@Qualifier("db7SqlSessionTemplate")
	private SqlSession sqlSession7;

	// 군산
	@Autowired
	@Qualifier("db8SqlSessionTemplate")
	private SqlSession sqlSession8;

	public String getNow() throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getNow");
	}

	public List<BackupDTO> getBackupList(BackupSearch nmcSearch) throws SQLException {
		return this.dbSelector(nmcSearch, "getBackupList");
	}

	public List<BackupDTO> getBackupListFull(BackupSearch nmcSearch) throws SQLException {
		return this.dbSelector(nmcSearch, "getBackupListFull");
	}

	public List<BackupDTO> getSaveSetNameList(BackupSearch nmcSearch) throws SQLException {
		return this.dbSelector(nmcSearch, "getSaveSetNameList");
	}

	public List<BackupDTO> getClientChange(BackupSearch nmcSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getClientChange", nmcSearch);
	}

	public List<BackupDTO> getBackupCdList(String cdNm) throws SQLException {
		switch (cdNm) {
		case "location":
			return sqlSession.selectList(NAMESPACE + "getLocationList");

		default:
			return null;
		}
	}

	public List<BackupChartDTO> getBackupChart(BackupSearch nmcSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getBackupChart", nmcSearch);
	}

	public List<BackupChartDTO> getBackupChartThisMonth(BackupSearch nmcSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getBackupChartThisMonth", nmcSearch);
	}

	List<BackupDTO> dbSelector(BackupSearch nmcSearch, String query) {
		List<BackupDTO> result = new ArrayList<BackupDTO>();
		switch (nmcSearch.getNlCd()) {
		case "S":
			result = sqlSession3.selectList(NAMESPACE + query, nmcSearch);
			break;
		case "G1":
			result = sqlSession4.selectList(NAMESPACE + query, nmcSearch);
			break;
		case "G1T":
			result = sqlSession5.selectList(NAMESPACE + query, nmcSearch);
			break;
		case "G3":
			result = sqlSession6.selectList(NAMESPACE + query, nmcSearch);
			break;
		case "G4":
			result = sqlSession7.selectList(NAMESPACE + query, nmcSearch);
			break;
		case "GS":
			result = sqlSession8.selectList(NAMESPACE + query, nmcSearch);
			break;
		}
		return result;
	}
}