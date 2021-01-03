package com.itsmv.api.calendar;

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
import com.itsmv.api.config.BaseTimeEntity;
import com.itsmv.api.server.ServerEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table(name = "calendar_child")
public class CalendarChildEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "calendar_date", nullable = false)
	private String calendarDate;

	@Column(name = "comment", nullable = false)
	private String comment;

	@Column(name = "server_id")
	private Long serverId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "server_id", insertable = false, updatable = false)
	private ServerEntity serverEntity;

}
