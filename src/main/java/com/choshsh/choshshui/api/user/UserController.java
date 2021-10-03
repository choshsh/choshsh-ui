package com.choshsh.choshshui.api.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

  private static final String PREFRIX_URL = "/api/user";
  private final UserRepo userRepo;

  public UserController(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @ApiOperation(value = "로그인 기능", notes = "userId, userPw 매핑")
  @PostMapping(value = PREFRIX_URL + "/login")
  public UserEntity login(
      @ApiParam(value = "userid, userPw", required = true)
      @RequestBody UserEntity userEntity) {
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

  @ApiOperation(value = "사용자 조회")
  @PostMapping(value = PREFRIX_URL + "/info")
  UserEntity info(@RequestBody UserEntity userEntity) {
    return userRepo.findByUserIdAndUserPw(userEntity.getUserId(), userEntity.getUserPw());
  }

  @ApiOperation(value = "사용자 등록")
  @PostMapping(value = PREFRIX_URL)
  UserEntity create(@RequestBody UserEntity userEntity) {
    return userRepo.save(userEntity);
  }

  @ApiOperation(value = "사용자 수정")
  @PutMapping(value = PREFRIX_URL + "/{id}")
  UserEntity update(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
    userEntity.setId(id);
    return userRepo.save(userEntity);
  }

  @ApiOperation(value = "사용자 삭제")
  @DeleteMapping(value = PREFRIX_URL + "/{id}")
  public void delete(@PathVariable("id") Long id) {
    userRepo.deleteById(id);
  }

}