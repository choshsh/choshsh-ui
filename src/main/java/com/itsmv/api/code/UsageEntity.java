package com.itsmv.api.code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Entity
@Table(name = "usage")
public class UsageEntity {

	@Id
	@Column(name = "usage_cd", nullable = false)
	private String usageCd;

	@Column(name = "usage_nm", nullable = false)
	private String usageNm;

}
