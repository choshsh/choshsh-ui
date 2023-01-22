package com.choshsh.choshshui.api.admin;

import com.choshsh.choshshui.api.enums.YN;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "header")
@NoArgsConstructor
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
