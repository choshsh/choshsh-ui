package com.choshsh.choshshui.api.user;

import io.swagger.v3.oas.annotations.Operation;
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

  @PostMapping(value = PREFRIX_URL + "/login")
  @Operation(
      description = "Login",
      method = "POST"
  )
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
  @Operation(description = "Get user")
  UserEntity info(@RequestBody UserEntity userEntity) {
    return userRepo.findByUserIdAndUserPw(userEntity.getUserId(), userEntity.getUserPw());
  }

  @PostMapping(value = PREFRIX_URL)
  @Operation(description = "Add an user")
  UserEntity create(@RequestBody UserEntity userEntity) {
    return userRepo.save(userEntity);
  }

  @PutMapping(value = PREFRIX_URL + "/{id}")
  @Operation(description = "Update an user")
  UserEntity update(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
    userEntity.setId(id);
    return userRepo.save(userEntity);
  }

  @DeleteMapping(value = PREFRIX_URL + "/{id}")
  @Operation(description = "Delete an user")
  public void delete(@PathVariable("id") Long id) {
    userRepo.deleteById(id);
  }

}