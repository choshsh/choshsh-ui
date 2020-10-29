package com.doms.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiDTO {
	private String diDate;
	private String diStartTime;
	private String diEndTime;
	private String diInspector;
	private String diIsError;
	private String diComment;
	private String regDate;
	private String regUser;
	private String modDate;
	private String modUser;
	private List<DieDTO> dieList;
	private int dieCnt;
	private String diStartTime2;
	private String diEndTime2;
	private String diInspector2;
	private String diIsError2;
	private String diComment2;
}
