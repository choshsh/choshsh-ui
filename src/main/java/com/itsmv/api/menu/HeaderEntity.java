package com.itsmv.api.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.itsmv.config.BaseDateEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "header", uniqueConstraints = @UniqueConstraint(columnNames = { "url", "name" }))
public class HeaderEntity extends BaseDateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String name;

}
