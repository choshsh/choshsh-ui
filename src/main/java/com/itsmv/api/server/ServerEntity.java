package com.itsmv.api.server;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.api.code.DeptEntity;
import com.itsmv.api.code.LocationEntity;
import com.itsmv.api.code.OprEntity;
import com.itsmv.api.code.OsEntity;
import com.itsmv.api.code.UsageEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table(name = "server")
public class ServerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "server_nm", nullable = false)
	private String serverNm;

	@Column(nullable = false)
	private String hostname;

	@Column(name = "mng_empl_nm")
	private String mngEmplNm;

	@Column(name = "location_detail")
	private String locationDetail;

	@Column(name = "ip_addr")
	private String ipAddr;

	@Column(name = "maker_nm")
	private String makerNm;

	@Column
	private Integer price;

	@Column
	private String serial;

	@Column(name = "buy_date", nullable = false, updatable = false)
	@CreationTimestamp
	private Date buyDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_cd", insertable = false, updatable = false)
	private DeptEntity deptEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_cd", insertable = false, updatable = false)
	private LocationEntity locationEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "opr_cd", insertable = false, updatable = false)
	private OprEntity oprEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usage_cd", insertable = false, updatable = false)
	private UsageEntity usageEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "os_cd", insertable = false, updatable = false)
	private OsEntity osEntity;

}
