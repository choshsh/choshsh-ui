package com.itsmv.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardSearch {
	private int bdSeq;
	private String title;
	private String content;
	private int userSeq;
	private String regDt;
	private String delYn;
	private String delDt;
	private String modDt;
	private String userName;
	private String bdCd;
	private String bdNm;

	public BoardSearch() {
	}

	public BoardSearch(int bdSeq) {
		this.bdSeq = bdSeq;
	}
}
