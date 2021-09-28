package com.choshsh.itsmv.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserCtrl {

  private static final String PREFRIX_URL = "/api/user";
  private final UserRepo userRepo;

  public UserCtrl(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @GetMapping(value = PREFRIX_URL)
  List<UserEntity> list() {
    List<UserEntity> list = new ArrayList<>();
    Iterable<UserEntity> it = userRepo.findAll();
    it.forEach(o -> {
      o.setUserPw("");
      list.add(o);
    });
    return list;
  }

  @PostMapping(value = PREFRIX_URL + "/login")
  public UserEntity login(@RequestBody UserEntity userEntity) {
    try {
      UserEntity user = userRepo.findByUserIdAndUserPw(userEntity.getUserId(),
          userEntity.getUserPw());
      user.setUserPw("");
      return user;
    } catch (Exception e) {
      log.warn("로그인 실패 ===> {}", userEntity.getUserId());
      return null;
    }
  }

  @PostMapping(value = PREFRIX_URL + "/info")
  UserEntity info(@RequestBody UserEntity userEntity) {
    return userRepo.findByUserIdAndUserPw(userEntity.getUserId(), userEntity.getUserPw());
  }

  @PostMapping(value = PREFRIX_URL)
  UserEntity create(@RequestBody UserEntity userEntity) {
    return userRepo.save(userEntity);
  }

  @PutMapping(value = PREFRIX_URL + "/{id}")
  UserEntity update(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
    userEntity.setId(id);
    return userRepo.save(userEntity);
  }

  @DeleteMapping(value = PREFRIX_URL + "/{id}")
  public void delete(@PathVariable("id") Long id) {
    userRepo.deleteById(id);
  }

}