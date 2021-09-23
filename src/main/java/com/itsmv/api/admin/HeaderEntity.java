package com.itsmv.api.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.api.enums.YN;
import com.itsmv.config.BaseColumnEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "header")
public class HeaderEntity extends BaseColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 500)
    private String url;

    private int sort;

    @Enumerated(EnumType.STRING)
    private YN isBlank;

}
