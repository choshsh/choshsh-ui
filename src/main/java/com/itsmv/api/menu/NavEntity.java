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
@javax.persistence.Table(name = "nav", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "to" }))
public class NavEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	private String _name;

	@Column
	@NotNull
	private String name;

	@Column
	@NotNull
	private String to;

	@Column
	@NotNull
	private String icon;
}
