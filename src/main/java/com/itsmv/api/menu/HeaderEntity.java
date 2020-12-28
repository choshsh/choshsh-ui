package com.itsmv.api.menu;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@javax.persistence.Entity
@javax.persistence.Table(name = "header", uniqueConstraints = @UniqueConstraint(columnNames = { "url", "name" }))
public class HeaderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	private String url;

	@Column
	@NotNull
	private String name;

}
