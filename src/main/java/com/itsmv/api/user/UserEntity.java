package com.itsmv.api.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.api.config.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table(name = "user")
public class UserEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	private Long userNo;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "user_pw", nullable = false)
	private String userPw;

}
