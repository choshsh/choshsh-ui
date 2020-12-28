package com.itsmv.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServerSearch extends ServerDTO {
	private String orderBy;
	private int monthParam;
	private String div;
}
