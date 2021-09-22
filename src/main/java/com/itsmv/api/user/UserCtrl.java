package com.itsmv.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserCtrl {

    private final UserRepo userRepo;

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

    @PostMapping(value = "/api/user/login")
    public UserEntity login(@RequestBody UserEntity userEntity) {
        try {
            UserEntity user = userRepo.findByUserIdAndUserPw(userEntity.getUserId(), userEntity.getUserPw());
            user.setUserPw("");
            return user;
        } catch (Exception e) {
            log.warn("로그인 실패 ===> {}", userEntity.getUserId());
            return null;
        }
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
        userEntity.setId(id);
        return userRepo.save(userEntity);
    }

    @DeleteMapping(value = "/api/user/{id}")
    public void delete(@PathVariable("id") Long id) {
        userRepo.deleteById(id);
    }

}