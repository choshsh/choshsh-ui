package com.itsmv.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BackupSearch extends BackupDTO{
	private String orderBy;
	private String stdDate;
	private String targetDate;
}
