package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VmDTO {
	protected String vmNo;
	protected String vmNm;
	protected String vmNickname;
	protected String vmClusterCd;
	protected String vmUsageCd;
	protected String vmOsCd;
	protected String vmIp;
	protected String vmRegDate;
	protected int vmCpuCore;
	protected int vmMemory;
	protected int vmDisk;
	protected String vmUseEmpl;
	protected String vmUseDept;
	protected String vmMngEmpl;
	protected String vmMngDept;
	protected String vmPowerOn;
	protected String vmOsLine;
	protected String vmOsDetail;
	protected String vmClusterNm;
	protected String vmClusterMaker;
	protected String vmClusterLocation;
	protected String vmClusterLocationNm;
	protected String vmUsageNm;
	protected int vmClusterMemory;
	protected String vmDelYn;
	protected String vmDelDate;
	protected String vmComment;
	protected String ciNo;
	protected String vmHostname;
}
