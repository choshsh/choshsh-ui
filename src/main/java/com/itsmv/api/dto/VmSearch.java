package com.itsmv.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VmSearch extends VmDTO {
	private String orderBy;
	private int monthParam;
	private String div;

	public VmSearch() {
	}

	public VmSearch(String vmNo) {
		this.vmNo = vmNo;
	}
}
