package com.itsmv.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserCtrl {

    private final UserRepo userRepo;

    @Autowired
    private UserCtrl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(value = "/api/user")
    List<UserEntity> list() {
        List<UserEntity> list = new ArrayList<>();
        Iterable<UserEntity> it = userRepo.findAll();
        it.forEach(list::add);
        return list;
    }

    @PostMapping(value = "/api/user/info")
    UserEntity info(@RequestBody UserEntity userEntity) {
        return userRepo.findByUserIdAndUserPw(userEntity.getUserId(), userEntity.getUserPw());
    }

    @PostMapping(value = "/api/user")
    UserEntity create(@RequestBody UserEntity userEntity) {
        return userRepo.save(userEntity);
    }

    @PutMapping(value = "/api/user/{id}")
    UserEntity update(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
        userEntity.setUserNo(id);
        return userRepo.save(userEntity);
    }

    @DeleteMapping(value = "/api/user/{id}")
    public void delete(@PathVariable("id") Long id) {
        userRepo.deleteById(id);
    }

}