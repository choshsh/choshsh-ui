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
@Table(name = "opr")
public class OprEntity {

	@Id
	@Column(name = "opr_cd", nullable = false)
	private String oprCd;

	@Column(name = "opr_nm", nullable = false)
	private String oprNm;

}
