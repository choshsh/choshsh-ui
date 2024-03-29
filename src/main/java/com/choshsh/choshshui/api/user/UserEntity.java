package com.choshsh.choshshui.api.user;

import com.choshsh.choshshui.config.BaseColumnEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.choshsh.choshshui.api.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "users")
public class UserEntity extends BaseColumnEntity {

  /**
   * PK 자동 생성
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;
  private String userName;
  private String userPw;

  @Enumerated(EnumType.STRING)
  private UserRole role;

}
