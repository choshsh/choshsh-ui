package com.itsmv.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VmChartDTO extends VmDTO {
	private String vmChartDate;
	private int VmSOpCnt;
	private int VmSDevCnt;
	private int VmGOpCnt;
	private int VmGDevCnt;
	private int ntCnt;
	private int linuxCnt;
	private int sOpCnt;
	private int sDevCnt;
	private int gOpCnt;
	private int gDevCnt;

	/* VM HOST */
	private String hostNm;
	private int cpuRate;
	private int memoryRate;

	/* VM DATASTORE */
	private String dsNm;
	private int totalSize;
	private int freeSize;
	private int usageRate;
}
