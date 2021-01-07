package com.itsmv.api.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCtrl {

	@Autowired
	private UserRepo userRepo;

	@GetMapping(value = "/api/user")
	List<UserEntity> list() {
		List<UserEntity> list = new ArrayList<>();
		Iterable<UserEntity> it = userRepo.findAll();
		it.forEach(o -> list.add(o));
		return list;
	}

	@PostMapping(value = "/api/user/info")
	UserEntity info(@RequestBody UserEntity userEntity) {
		return userRepo.findByUserIdAndUserPw(userEntity.getUserId(), userEntity.getUserPw());
	}

	@PostMapping(value = "/api/user")
	UserEntity create(@RequestBody UserEntity userEntity) {
		UserEntity created = userRepo.save(userEntity);
		return created;
	}

	@PutMapping(value = "/api/user/{id}")
	UserEntity update(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
		userEntity.setUserNo(id);
		UserEntity updated = userRepo.save(userEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/user/{id}")
	public void delete(@PathVariable("id") Long id) {
		userRepo.deleteById(id);
	}

}