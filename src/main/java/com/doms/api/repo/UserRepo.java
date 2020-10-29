package com.doms.api.repo;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doms.api.dto.UserDTO;

@Repository
public class UserRepo {
	protected static final String NAMESPACE = "com.doms.user.";

	@Autowired
	private SqlSession sqlSession;

	public int getUserCount(UserDTO userDTO) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getUserCount", userDTO);
	}

	public UserDTO getUser(String userId) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + "getUser", userId);
	}

	public List<UserDTO> getUserList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + "getUserList");
	}
}