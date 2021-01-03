package com.itsmv.api.calendar;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.api.config.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table(name = "calendar")
public class CalendarEntity extends BaseTimeEntity {

	@Id
	@Column(name = "calendar_date", nullable = false)
	private String calendarDate;

	@Column(name = "start_time", nullable = false)
	private String startTime;

	@Column(name = "end_time", nullable = false)
	private String endTime;

	@Column(name = "reg_user", nullable = false)
	private String regUser;

	@Column(name = "is_error", length = 1)
	private String isError;

	@Column(name = "comment")
	private String comment;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "calendar_date", insertable = false, updatable = false)
	private Set<CalendarChildEntity> calendarChildEntity = new LinkedHashSet<>();

	@Column(name = "error_cnt", nullable = true)
	private Integer errorCnt;

}
