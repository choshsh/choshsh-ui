package com.itsmv.api.calendar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.api.server.ServerEntity;
import com.itsmv.config.BaseDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "calendar_child")
public class CalendarChildEntity extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String calendarDate;

    @Column(nullable = false)
    private String comment;

    private Long serverId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serverId", insertable = false, updatable = false)
    private ServerEntity serverEntity;

}
