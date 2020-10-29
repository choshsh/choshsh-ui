package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EgressionDTO {
	protected String teamNm;
	protected String mbrNm;
	protected String egDate;
	protected String egLocDtl;
	protected String egObjectDtl;
	protected String siteWork;
	protected String sDate;
	protected String eDate;
	protected String egObject;
	protected String sTime;
	protected String eTime;
	protected int cnt;
	protected String egStatusNm;
	protected String egStatus;
	protected String mngTeam;
}
