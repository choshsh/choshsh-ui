package com.choshsh.choshshui.api.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

  UserEntity findByUserIdAndUserPw(String userId, String userPw);

}
