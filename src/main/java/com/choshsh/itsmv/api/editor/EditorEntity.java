package com.choshsh.itsmv.api.editor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.choshsh.itsmv.config.BaseDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "editor")
public class EditorEntity extends BaseDateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long entityId;

  @Column(nullable = false)
  private String title;

  @Column
  private String keyword;

  @Column(nullable = false, length = 5000)
  private String content;

}
