package com.choshsh.choshshui.api.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "envvar")
public class EnvEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "env_key")
  private String key;

  @Column(name = "env_value" ,length = 1000)
  private String value;

}
