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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "nav", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "url" }))
public class NavEntity extends BaseDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String _name;

	@Column(nullable = false)

	private String name;

	@Column(name = "url", nullable = false)
	private String to;

	@Column(nullable = false)
	private String icon;
}
