package com.choshsh.choshshui.api.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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

  @NotBlank(message = "category must not be blank")
  private String category;

  @NotBlank(message = "name must not be blank")
  private String name;

  @NotBlank(message = "url must not be blank")
  @Column(length = 500)
  private String url;

  @NotBlank(message = "height must not be blank")
  private int height;

  @Column(length = 500)
  private String tooltip;

  private int sort;

}
