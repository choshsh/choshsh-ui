package com.doms.api.repo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doms.api.dto.VmChartDTO;
import com.doms.api.dto.VmDTO;
import com.doms.api.dto.VmSearch;

@Repository
public class VmRepo {
	protected static final String NAMESPACE = "com.doms.vm.";

	@Autowired
	private SqlSession sqlSession;

	public int getVmCount(VmSearch vmSearch) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getVmCount", vmSearch);
	}

	public VmDTO getVm(VmSearch vmSearch) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getVm", vmSearch);
	}

	public List<VmDTO> getVmList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getVmList");
	}

	public List<VmDTO> getVmList(VmSearch vmSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getVmList", vmSearch);
	}

	public List<VmDTO> getVmListAll(VmSearch vmSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getVmListAll", vmSearch);
	}

	public List<Integer> getVmChartTotal(VmSearch vmSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getVmChartTotal", vmSearch);
	}

	public List<Integer> getVmChartTotalDel(VmSearch vmSearch) throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getVmChartTotalDel", vmSearch);
	}

	public List<VmDTO> getVmCdList(String cdNm) throws SQLException {
		switch (cdNm) {
		case "cluster":
			return sqlSession.selectList(NAMESPACE + "getVmClusterList");
		case "os":
			return sqlSession.selectList(NAMESPACE + "getVmOsList");
		case "usage":
			return sqlSession.selectList(NAMESPACE + "getVmUsageList");

		default:
			return null;
		}
	}

	public VmChartDTO getVmChartMonthly() throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getVmChartMonthly");
	}

	public List<VmChartDTO> getVmDatastore() throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getVmDatastore");
	}

	public List<VmChartDTO> getVmHost() throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getVmHost");
	}

	public List<HashMap<String, Object>> getCountPerOsDetailTotal() throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getCountPerOsDetailTotal");
	}

	public int insertVm(VmDTO vmDTO) {
		return sqlSession.insert(NAMESPACE + "insertVm", vmDTO);
	}

	public int updateVm(VmDTO vmDTO) {
		return sqlSession.update(NAMESPACE + "updateVm", vmDTO);
	}

	public int delVm(VmSearch vmSearch) {
		return sqlSession.update(NAMESPACE + "delVm", vmSearch);
	}
}