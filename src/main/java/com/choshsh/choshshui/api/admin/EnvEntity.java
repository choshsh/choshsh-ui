package com.choshsh.choshshui.api.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

  @NotBlank
  @Column(name = "env_key")
  private String key;

  @NotBlank
  @Column(name = "env_value" ,length = 1000)
  private String value;

}
