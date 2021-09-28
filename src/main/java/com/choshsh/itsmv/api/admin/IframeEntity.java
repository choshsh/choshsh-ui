package com.choshsh.itsmv.api.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.choshsh.itsmv.config.BaseColumnEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "iframe")
public class IframeEntity extends BaseColumnEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String category;

  private String name;

  @Column(length = 500)
  private String url;

  private int height;

  private int sort;

}
