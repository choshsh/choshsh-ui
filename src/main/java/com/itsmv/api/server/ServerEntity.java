package com.itsmv.api.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.api.enums.LocationGroup;
import com.itsmv.api.enums.ServerOperationGroup;
import com.itsmv.api.enums.ServerOsGroup;
import com.itsmv.api.enums.ServerUsageGroup;
import com.itsmv.config.BaseDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "server")
public class ServerEntity extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serverNm;

    @Column(nullable = false)
    private String hostname;

    private String mngEmplNm;


    private String ipAddr;

    private String makerNm;

    private String makerModelNm;

    private Integer warranty;

    private Integer price;

    private String spec;

    @Column(nullable = false, updatable = false)
    private Date buyDate;

    private ServerOsGroup os;

    private LocationGroup location;

    private ServerUsageGroup serverUsage;

    private ServerOperationGroup opr;

}
