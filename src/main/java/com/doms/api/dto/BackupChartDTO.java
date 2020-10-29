package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BackupChartDTO {
	private String nmcChartDate;
	private int vtlClientCnt;
	private float vtlFullSum;
	private float vtlIncrSum;
	private int ptlClientCnt;
	private float ptlFullSum;
	private float ptlIncrSum;
	private int ltoClientCnt;
	private float ltoFullSum;
	private float ltoIncrSum;
}
