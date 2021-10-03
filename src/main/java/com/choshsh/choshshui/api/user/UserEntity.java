package com.choshsh.choshshui.api.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.choshsh.choshshui.api.enums.UserRole;
import com.choshsh.choshshui.config.BaseDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table(name = "user")
public class UserEntity extends BaseDateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;
	private String userName;
	private String userPw;

	@Enumerated(EnumType.STRING)
	private UserRole role;

}
