package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EgressionSearch extends EgressionDTO{
	private String orderBy;
	private String scope;
}
