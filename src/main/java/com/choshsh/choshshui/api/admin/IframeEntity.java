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
@Table(name = "iframe")
public class IframeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String category;

  private String name;

  @Column(length = 500)
  private String url;

  private int height;

  @Column(length = 500)
  private String tooltip;

  private int sort;

}
