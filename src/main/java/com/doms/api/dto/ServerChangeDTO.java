package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServerChangeDTO {
	private String chgDate;
	private String chgTime;
	private String chgGbnCd;
	private String chgGbnNm;
	private String chgBeforeText;
	private String chgAfterText;
	private String chgText;
}
