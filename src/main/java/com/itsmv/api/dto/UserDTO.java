package com.itsmv.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO {
	private int userSeq;
	private String userId;
	private String userPw;
	private String userName;
	private String del;
	private String rDate;
	private String mDate;
	private String dDate;
	private String userRoleCd;
}
