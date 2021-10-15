package com.choshsh.choshshui.api.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

  UserEntity findByUserIdAndUserPw(@Param("userId") String userId, @Param("userPw") String userPw);

}
