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
@Table(name = "os")
public class OsEntity {

	@Id
	@Column(name = "os_cd", nullable = false)
	private String osCd;

	@Column(name = "os_nm", nullable = false)
	private String osNm;

	@Column(name = "os_detail", nullable = false)
	private String osDetail;

}
