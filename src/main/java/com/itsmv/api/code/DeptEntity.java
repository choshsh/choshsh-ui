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
@Table(name = "dept")
public class DeptEntity {

	@Id
	@Column(name = "dept_cd", nullable = false)
	private String deptCd;

	@Column(name = "dept_nm", nullable = false)
	private String deptNm;

}
