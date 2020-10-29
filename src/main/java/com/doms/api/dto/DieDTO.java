package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DieDTO {
	private int dieSeq;
	private String diDate;
	private String ciNo;
	private String dieComment;
	private String dieRegDate;
	private String dieRegUser;
	private String dieModDate;
	private String dieModUser;
}
