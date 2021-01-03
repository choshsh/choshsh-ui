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
@Table(name = "location")
public class LocationEntity {

	@Id
	@Column(name = "location_cd", nullable = false)
	private String locationCd;

	@Column(name = "location_nm", nullable = false)
	private String locationNm;

}
