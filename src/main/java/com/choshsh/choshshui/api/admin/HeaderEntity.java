package com.choshsh.choshshui.api.admin;


import com.choshsh.choshshui.api.enums.YN;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "header")
public class HeaderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "name must not be blank")
  private String name;

  @NotBlank(message = "url must not be blank")
  @Column(length = 500)
  private String url;

  private int sort;

  @Enumerated(EnumType.STRING)
  private YN isBlank;

}
