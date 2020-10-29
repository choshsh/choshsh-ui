package com.doms.api.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doms.api.dto.UserDTO;
import com.doms.api.repo.UserRepo;

@Service
public class UserSvc {
	@Autowired
	private UserRepo userRepo;

	public int checkLogin(UserDTO userDTO) throws Exception {
		return userRepo.getUserCount(userDTO);
	}

	public UserDTO getUser(String userId) throws Exception {
		return userRepo.getUser(userId);
	}

	public List<UserDTO> getUserList() throws Exception {
		return userRepo.getUserList();
	}
}
