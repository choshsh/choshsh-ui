package com.itsmv.api.calendar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsmv.config.BaseDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "calendar")
public class CalendarEntity extends BaseDateEntity {

    @Id
    @Column(nullable = false)
    private String calendarDate;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private String regUser;

    @Column(length = 1)
    private String isError;

    private String comment;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendarDate", insertable = false, updatable = false)
    private Set<CalendarChildEntity> calendarChildEntity = new LinkedHashSet<>();

    @Column(nullable = true)
    private Integer errorCnt;

}
