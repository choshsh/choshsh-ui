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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.api.code.DeptEntity;
import com.itsmv.api.code.LocationEntity;
import com.itsmv.api.code.OprEntity;
import com.itsmv.api.code.OsEntity;
import com.itsmv.api.code.UsageEntity;
import com.itsmv.api.config.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table(name = "server")
public class ServerEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "server_id")
	private Long serverId;

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

	@Column(name = "maker_model_nm")
	private String makerModelNm;

	@Column(name = "warranty")
	private Integer warranty;

	@Column
	private Integer price;

	@Column
	private String serial;

	@Column
	private String spec;

	@Column(name = "buy_date", nullable = false, updatable = false)
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

	private String deptNm;
	private String locationNm;
	private String oprNm;
	private String usageNm;
	private String osNm;
	private String osDetail;

}
