package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BackupDTO {
	private int workflowJobId;
	private String clientName;
	private String groupName;
	private String workflowStatus;
	private String actionName;
	private String workflowStartTime;
	private String workflowEndTime;
	private String workflowStartDate;
	private String workflowEndDate;
	private String term;
	private String backupLevel;
	private float saveSetSize;
	private String saveSetName;
	private int saveSetNameCnt;
	private String nlCd;
	private String nlNm;
	private String policyName;
}
