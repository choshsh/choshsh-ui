package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
	private int bdSeq;
	private String title;
	private String content;
	private int userSeq;
	private int modUserSeq;
	private String regDt;
	private String delYn;
	private String delDt;
	private String modDt;
	private String userName;
	private String modUserName;
	private String bdCd;
	private String bdNm;
	private String keyword;
}
